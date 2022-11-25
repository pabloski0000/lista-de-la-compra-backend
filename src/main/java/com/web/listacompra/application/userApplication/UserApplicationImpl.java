package com.web.listacompra.application.userApplication;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.OverridesAttribute;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.web.listacompra.domain.possibleSubscriberDomain.PossibleSubscriber;
import com.web.listacompra.domain.possibleSubscriberDomain.PossibleSubscriberRepository;
import com.web.listacompra.domain.userDomain.User;
import com.web.listacompra.domain.userDomain.UserRepository;
import com.web.listacompra.security.securityConfiguration.GrantedAuthorities;
import com.web.listacompra.systemConfiguration.EnvironmentProperties;
import com.web.listacompra.utils.JWTGenerator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Service
public class UserApplicationImpl implements UserApplication {
    private final UserRepository userRepository;
    private final PossibleSubscriberRepository possibleSubscriberRepository;
    private final ModelMapper modelMapper;
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    @Autowired
    public UserApplicationImpl(final UserRepository userRepository, final ModelMapper modelMapper, final PossibleSubscriberRepository possibleSubscriberRepository, final ReactiveMongoTemplate reactiveMongoTemplate){
        this.userRepository = userRepository;
        this.possibleSubscriberRepository = possibleSubscriberRepository;
        this.modelMapper = modelMapper;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }
    @Override
    public RegisterUserOutDto registerAdmin(RegisterAdminDto registerAdminDto){
        User user = modelMapper.map(registerAdminDto, User.class);
        if(userRepository.findUserByNickName(user.getNickName()).isPresent()){
            //Inform user about error
            //This might be implemented in the security layer
            RegisterUserOutDto registerUserOutDto = new RegisterUserOutDto();
            registerUserOutDto.setAccessToken("Ya existe algún usuario en BBDD");
            return registerUserOutDto;
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(GrantedAuthorities.ADMIN);
        register(user, authorities);
        Optional<User> storedUser = userRepository.addUser(user);
        return modelMapper.map(storedUser.get(), RegisterUserOutDto.class);
    }
    @Override
    public void registerUser(RegisterPossibleSubscriberDto registerPossibleSubscriberDto){
        int code = (int) (Math.random() * 10000000);
        PossibleSubscriber possibleSubscriber = modelMapper.map(registerPossibleSubscriberDto, PossibleSubscriber.class);
        possibleSubscriber.setCode(code);
        possibleSubscriberRepository.addPossibleSubscriber(possibleSubscriber);
    }
    @Override
    public RegisterUserOutDto confirmRegistration(RegisterUserDto registerUserDto){
        RegisterUserOutDto registerUserOutDtoIfFails = new RegisterUserOutDto();
        Optional<PossibleSubscriber> optionalPossibleSubscriber = possibleSubscriberRepository.findOneByNickName(registerUserDto.getNickName());
        if(!optionalPossibleSubscriber.isPresent()){
            registerUserOutDtoIfFails.setAccessToken("There isn't any possibleUser with this nickName: " + registerUserDto.getNickName());
            return registerUserOutDtoIfFails;
        }
        PossibleSubscriber possibleSubscriber = optionalPossibleSubscriber.get();
        if(registerUserDto.getCode() != possibleSubscriber.getCode()){
            registerUserOutDtoIfFails.setAccessToken("Wrong code: " + registerUserDto.getCode());
            return registerUserOutDtoIfFails;
        }
        User user = modelMapper.map(optionalPossibleSubscriber.get(), User.class);
        register(user, new ArrayList<GrantedAuthority>());
        Optional<User> storedUser = userRepository.addUser(user);
        return modelMapper.map(storedUser.get(), RegisterUserOutDto.class);
    }
    private void register(User user, List<GrantedAuthority> authorities){
        String secretKey = EnvironmentProperties.getSingleton().getSecretKey();
        user.setAccessToken(
            JWTGenerator.generateStandardJWT(
                user.getNickName(),
                authorities,
                secretKey,
                new Date(System.currentTimeMillis() + 24 * 3600 * 1000)
                )
            );
    }
    @Override
    public void deleteMyself(){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
            (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteUserByNickName(usernamePasswordAuthenticationToken.getName());
    }
    @Override
    public Flux<PossibleSubscriberOutDto> notifyMeOfPossibleSubscribers() {
        return possibleSubscriberRepository.findAllTailable()
        .map(it -> {
            return modelMapper.map(it, PossibleSubscriberOutDto.class);
        }
        );
        /*return userRepository.findAllPossibleSubscribersTailable()
        .doOnNext(it -> System.out.println("Tailable"))
        .map(user -> {
            List<PossibleSubscriber> possibleSubscribers = user.getPossibleSubscribers();
            List<PossibleSubscriberOutDto> possibleSubscriberOutDtos = new ArrayList<>();
            for (PossibleSubscriber possibleSubscriber : possibleSubscribers) {
                PossibleSubscriberOutDto possibleSubscriberOutDto = modelMapper.map(possibleSubscriber, PossibleSubscriberOutDto.class);
                possibleSubscriberOutDtos.add(possibleSubscriberOutDto);
            }
            return possibleSubscriberOutDtos;
        })*/
    }
}
