package com.web.listacompra.infrastracture.userRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.web.listacompra.application.userApplication.PossibleSubscriberOutDto;
import com.web.listacompra.domain.possibleSubscriberDomain.PossibleSubscriber;
import com.web.listacompra.domain.userDomain.User;
import com.web.listacompra.domain.userDomain.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserConnectionMongoDBRepository userConnectionMongoDBRepository;
    private final UserConnectionReactiveMongoDBRepository userConnectionReactiveMongoDBRepository;
    @Autowired
    public UserRepositoryImpl(final UserConnectionMongoDBRepository userConnectionMongoDBRepository, final UserConnectionReactiveMongoDBRepository userConnectionReactiveMongoDBRepository){
        this.userConnectionMongoDBRepository = userConnectionMongoDBRepository;
        this.userConnectionReactiveMongoDBRepository = userConnectionReactiveMongoDBRepository;
    }
    @Override
    public Mono<User> addUserReactively(User user) {
        user.setNew(true);
        Mono<User> storedUser = userConnectionReactiveMongoDBRepository.save(user);
        storedUser.doOnNext(it -> {
            it.setNew(false);
            user.setNew(false);
        });
        return storedUser;
    }
    @Override
    public Optional<User> addUser(User user) {
        user.setNew(true);
        User storedUser = userConnectionMongoDBRepository.save(user);
        user.setNew(false);
        return Optional.of(storedUser);
    }
    @Override
    public Optional<User> findUserByNickName(String nickName) {
        Optional<User> userOp = userConnectionMongoDBRepository.findUserByNickName(nickName);
        return userOp;
    }
    @Override
    public Flux<User> findUserByNickNameReactively(String nickName) {
        return userConnectionReactiveMongoDBRepository.findUserByNickName(nickName);
    }
    @Override
    public List<User> getAllUsers() {
        return userConnectionMongoDBRepository.findAll();
    }
    @Override
    public void deleteUserByNickName(String nickName) {
        userConnectionMongoDBRepository.deleteUserByNickName(nickName);
    }
}
