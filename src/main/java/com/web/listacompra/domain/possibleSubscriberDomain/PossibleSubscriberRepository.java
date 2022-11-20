package com.web.listacompra.domain.possibleSubscriberDomain;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PossibleSubscriberRepository {
    Flux<PossibleSubscriber> findAllTailable();
    Mono<PossibleSubscriber> addPossibleSubscriberReactively(PossibleSubscriber possibleSubscriber);
    PossibleSubscriber addPossibleSubscriber(PossibleSubscriber possibleSubscriber);
    Optional<PossibleSubscriber> findOneByNickName(String nickName);
}
