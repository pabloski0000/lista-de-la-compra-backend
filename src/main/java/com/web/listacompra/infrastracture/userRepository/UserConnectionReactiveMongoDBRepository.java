package com.web.listacompra.infrastracture.userRepository;

import com.web.listacompra.domain.userDomain.User;

import reactor.core.publisher.Flux;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserConnectionReactiveMongoDBRepository extends ReactiveMongoRepository<User, String> {
    Flux<User> findUserByNickName(String nickName);
}
