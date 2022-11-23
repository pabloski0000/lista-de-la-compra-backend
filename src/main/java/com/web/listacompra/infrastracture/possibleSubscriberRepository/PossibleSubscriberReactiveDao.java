package com.web.listacompra.infrastracture.possibleSubscriberRepository;

import com.web.listacompra.domain.possibleSubscriberDomain.PossibleSubscriber;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PossibleSubscriberReactiveDao {
    Flux<PossibleSubscriber> getInSync();
    Mono<PossibleSubscriber> save(PossibleSubscriber possibleSubscriber);
}
