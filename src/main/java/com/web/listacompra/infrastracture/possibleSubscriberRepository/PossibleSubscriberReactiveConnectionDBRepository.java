package com.web.listacompra.infrastracture.possibleSubscriberRepository;

import java.util.List;

import com.web.listacompra.domain.possibleSubscriberDomain.PossibleSubscriber;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PossibleSubscriberReactiveConnectionDBRepository extends ReactiveMongoRepository<PossibleSubscriber, String> {
    @Tailable
    @Query("{}")
    Flux<PossibleSubscriber> findAllTailable();
    Mono<PossibleSubscriber> findOneByNickName(String nickName);
}
