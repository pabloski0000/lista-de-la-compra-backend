package com.web.listacompra.infrastracture.possibleSubscriberRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;

import com.web.listacompra.domain.possibleSubscriberDomain.PossibleSubscriber;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PossibleSubscriberReactiveDaoImpl implements PossibleSubscriberReactiveDao {
    private PossibleSubscriberReactiveMongoDb possibleSubscriberReactiveMongoDb;
    private PossibleSubscriberMongoDb possibleSubscriberMongoDb;
    @Autowired
    public PossibleSubscriberReactiveDaoImpl(PossibleSubscriberReactiveMongoDb possibleSubscriberReactiveMongoDb, PossibleSubscriberMongoDb possibleSubscriberMongoDb){
        this.possibleSubscriberReactiveMongoDb = possibleSubscriberReactiveMongoDb;
        this.possibleSubscriberMongoDb = possibleSubscriberMongoDb;
    }
    @Override
    public Flux<PossibleSubscriber> getInSync() {
        preventInfiniteStreamToBecomeDead();
        return possibleSubscriberReactiveMongoDb.findAllTailable();
    }
    private void preventInfiniteStreamToBecomeDead(){
        List<PossibleSubscriber> allPosibleSubscriberInBBDD = possibleSubscriberMongoDb.findAll();
        if(allPosibleSubscriberInBBDD.size() == 0){
            final String informDBClientIsNotARealUser = "Special Case Document";
            PossibleSubscriber firstPossibleSubscriberSoThisCollectionReturnsAtLeastOneDocumentAndNotBecomeDead = 
                new PossibleSubscriber();
            firstPossibleSubscriberSoThisCollectionReturnsAtLeastOneDocumentAndNotBecomeDead.setNickName(informDBClientIsNotARealUser);
            firstPossibleSubscriberSoThisCollectionReturnsAtLeastOneDocumentAndNotBecomeDead.setCode(-1);
            possibleSubscriberMongoDb.save(firstPossibleSubscriberSoThisCollectionReturnsAtLeastOneDocumentAndNotBecomeDead);
        }
    }
    @Override
    public Mono<PossibleSubscriber> save(PossibleSubscriber possibleSubscriber) {
        return possibleSubscriberReactiveMongoDb.save(possibleSubscriber);
    }
}
