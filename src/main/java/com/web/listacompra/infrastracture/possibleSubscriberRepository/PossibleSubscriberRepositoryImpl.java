package com.web.listacompra.infrastracture.possibleSubscriberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.web.listacompra.domain.possibleSubscriberDomain.PossibleSubscriber;
import com.web.listacompra.domain.possibleSubscriberDomain.PossibleSubscriberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PossibleSubscriberRepositoryImpl implements PossibleSubscriberRepository {
    private final PossibleSubscriberReactiveConnectionDBRepository possibleSubscriberReactiveConnectionDBRepository;
    private final PossibleSubscriberConnectionDBRepository possibleSubscriberConnectionDBRepository;
    @Autowired
    public PossibleSubscriberRepositoryImpl(final PossibleSubscriberReactiveConnectionDBRepository possibleSubscriberReactiveConnectionDBRepository, final PossibleSubscriberConnectionDBRepository possibleSubscriberConnectionDBRepository){
        this.possibleSubscriberReactiveConnectionDBRepository = possibleSubscriberReactiveConnectionDBRepository;
        this.possibleSubscriberConnectionDBRepository = possibleSubscriberConnectionDBRepository;
    }
    @Override
    public Flux<PossibleSubscriber> findAllTailable() {
        return possibleSubscriberReactiveConnectionDBRepository.findAllTailable();
    }
    @Override
    public Mono<PossibleSubscriber> addPossibleSubscriberReactively(PossibleSubscriber possibleSubscriber) {
        possibleSubscriber.setNew(true);
        Mono<PossibleSubscriber> storedPossibleSubscriber = possibleSubscriberReactiveConnectionDBRepository.save(possibleSubscriber);
        storedPossibleSubscriber.doOnNext(it -> {
            it.setNew(false);
            possibleSubscriber.setNew(false);
        });
        return storedPossibleSubscriber;
    }
    @Override
    public PossibleSubscriber addPossibleSubscriber(PossibleSubscriber possibleSubscriber) {
        possibleSubscriber.setNew(true);
        PossibleSubscriber storedPossibleSubscriber = possibleSubscriberConnectionDBRepository.save(possibleSubscriber);
        possibleSubscriber.setNew(false);
        return storedPossibleSubscriber;
    }
    @Override
    public Optional<PossibleSubscriber> findOneByNickName(String nickName) {
        PossibleSubscriber possibleSubscriber = new PossibleSubscriber();
        possibleSubscriber.setNickName(nickName);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
        .withIgnorePaths("code");
        return possibleSubscriberConnectionDBRepository.findOne(Example.of(possibleSubscriber, exampleMatcher));
    }
    
}
