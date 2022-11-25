package com.web.listacompra.herokuApplication.newRegistrationStreamHerokuApplication;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.web.listacompra.application.userApplication.PossibleSubscriberOutDto;
import com.web.listacompra.application.userApplication.UserApplication;

import reactor.core.publisher.Flux;

@Service
public class NewRegistrationStreamEventImpl implements NewRegistrationStreamEvent, Runnable {
    private final int MAX_SECONDS_WITHOUT_NEW_EVENT_THROUGH_FLUX;
    private static final String NICKNAME_FOR_CONNECTION_CONTROL_EVENT = "Connection Control Event";
    private static final int CODE_FOR_CONNECTION_CONTROL_EVENT = -1;
    private Flux<?> connectionStream;
    private ScheduledExecutorService scheduledExecutorService;
    private ScheduledFuture<?> scheduledFuture;
    private UserApplication userApplication;
    private Flux<PossibleSubscriberOutDto> newRegistrationsStream;
    private Set<NewRegistrationStreamEventObserver> observers;
    public NewRegistrationStreamEventImpl(
        UserApplication userApplication,
        TimeToPreventIdleConnectionInServer timeToPreventIdleConnectionInServer
        ){
        this.userApplication = userApplication;
        this.scheduledExecutorService = Executors.newScheduledThreadPool(3);
        MAX_SECONDS_WITHOUT_NEW_EVENT_THROUGH_FLUX = timeToPreventIdleConnectionInServer.getSeconds();
        observers = new HashSet<>();
    }
    public Flux<PossibleSubscriberOutDto> subscribe(){
        return null;
    }
    /*Flux<?> manageConnection(Flux<?> connectionStream){
        connectionStream
        .subscribe(message -> {
            newMessageThroughFlux();
        });
    }
    private void newMessageThroughFlux(){
        scheduledFuture = executorService.schedule(this, MAX_SECONDS_WITHOUT_NEW_EVENT_THROUGH_FLUX, TimeUnit.SECONDS);
    }
    private void populateConnectionWithConnectionControlMessage(){
        Flux.creat
    }
    @Override
    public void timeElapsed() {
        // TODO Auto-generated method stub
    }*/
    @Override
    public void run() {
        notifyObserversOfConnectionControlMessage();
        newMessageHasBeenSent();
    }
    private void notifyObservers(){
        newRegistrationsStream
        .subscribe((PossibleSubscriberOutDto possibleSubscriberOutDto) -> {
            newMessageHasBeenSent();
            NewRegistrationEvent newRegistrationEvent = 
                new NewRegistrationEvent(possibleSubscriberOutDto.getNickName(), possibleSubscriberOutDto.getCode());
            observers.forEach(observer -> observer.notify(newRegistrationEvent));
        });
    }
    @Override
    public void subscribe(NewRegistrationStreamEventObserver observer) {
        observers.add(observer);
        if(newRegistrationsStream == null){
            newRegistrationsStream = userApplication.notifyMeOfPossibleSubscribers();
            notifyObservers();
        }
    }
    private void newMessageHasBeenSent(){
        if(scheduledFuture != null){
            scheduledFuture.cancel(false);
        }
        scheduledFuture = scheduledExecutorService
            .schedule(this, MAX_SECONDS_WITHOUT_NEW_EVENT_THROUGH_FLUX, TimeUnit.SECONDS);
    }
    private void notifyObserversOfConnectionControlMessage(){
        NewRegistrationEvent connectionControlEvent = 
            new NewRegistrationEvent(NICKNAME_FOR_CONNECTION_CONTROL_EVENT, CODE_FOR_CONNECTION_CONTROL_EVENT);
        observers.forEach(observer -> observer.notify(connectionControlEvent));
    }
}
