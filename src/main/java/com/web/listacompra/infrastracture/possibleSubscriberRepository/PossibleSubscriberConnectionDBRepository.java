package com.web.listacompra.infrastracture.possibleSubscriberRepository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.web.listacompra.domain.possibleSubscriberDomain.PossibleSubscriber;

public interface PossibleSubscriberConnectionDBRepository extends MongoRepository<PossibleSubscriber, String> {
    
}
