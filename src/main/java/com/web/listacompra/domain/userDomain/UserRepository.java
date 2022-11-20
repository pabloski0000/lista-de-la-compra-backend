package com.web.listacompra.domain.userDomain;

import java.util.List;
import java.util.Optional;

import com.web.listacompra.application.userApplication.PossibleSubscriberOutDto;
import com.web.listacompra.domain.possibleSubscriberDomain.PossibleSubscriber;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    List<User> getAllUsers();
    Mono<User> addUserReactively(User user);
    Optional<User> addUser(User user);
    Optional<User> findUserByNickName(String nickName);
    Flux<User> findUserByNickNameReactively(String nickName);
    void deleteUserByNickName(String nickName);
}
