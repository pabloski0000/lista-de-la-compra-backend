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
import com.web.listacompra.systemConfiguration.ApplicationProperties;
import com.web.listacompra.utils.JWTGenerator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        user.setRole("ROLE_ADMIN");
        List<String> roles = new ArrayList<>();
        roles.add(user.getRole());
        String secretKey = ApplicationProperties.getSingleton().getSecretKey();
        user.setAccessToken(JWTGenerator.generateStandardJWT(user.getNickName(), roles, secretKey, new Date(System.currentTimeMillis() + 24 * 3600 * 1000)));
        Optional<User> storedUser = userRepository.addUser(user);
        return modelMapper.map(storedUser.get(), RegisterUserOutDto.class);
    }
    @Override
    public void registerPossibleSubscriber(RegisterPossibleSubscriberDto registerPossibleSubscriberDto){
        int code = (int) (Math.random() * 10000000);
        PossibleSubscriber possibleSubscriber = modelMapper.map(registerPossibleSubscriberDto, PossibleSubscriber.class);
        possibleSubscriber.setCode(code);
        possibleSubscriberRepository.addPossibleSubscriber(possibleSubscriber);
    }
    @Override
    public RegisterUserOutDto registerUser(RegisterUserDto registerUserDto){
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
        user.setRole("ROLE_USER");
        List<String> roles = new ArrayList<>();
        roles.add(user.getRole());
        user.setAccessToken(JWTGenerator.generateStandardJWT(user.getNickName(), roles, "inventado", new Date(System.currentTimeMillis() + 24 * 3600 * 1000)));
        Optional<User> storedUser = userRepository.addUser(user);
        return modelMapper.map(storedUser.get(), RegisterUserOutDto.class);
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
        .map(it -> modelMapper.map(it, PossibleSubscriberOutDto.class));
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
    @Override
    public Flux<ChangeStreamEvent<PossibleSubscriber>> prueba(){
        Flux<ChangeStreamEvent<PossibleSubscriber>> variable = reactiveMongoTemplate.changeStream(PossibleSubscriber.class)
        .watchCollection(PossibleSubscriber.class)
        .listen();
        return variable;
    }
}
