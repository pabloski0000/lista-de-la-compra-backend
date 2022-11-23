package com.web.listacompra.infrastracture.possibleSubscriberRepository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.web.listacompra.domain.possibleSubscriberDomain.PossibleSubscriber;

public interface PossibleSubscriberMongoDb extends MongoRepository<PossibleSubscriber, String> {
    
}
