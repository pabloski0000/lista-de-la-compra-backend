package com.web.listacompra.controller.userController;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import com.web.listacompra.application.userApplication.PossibleSubscriberOutDto;
import com.web.listacompra.application.userApplication.RegisterAdminDto;
import com.web.listacompra.application.userApplication.RegisterPossibleSubscriberDto;
import com.web.listacompra.application.userApplication.RegisterUserDto;
import com.web.listacompra.application.userApplication.RegisterUserOutDto;
import com.web.listacompra.application.userApplication.UserApplication;
import com.web.listacompra.domain.possibleSubscriberDomain.PossibleSubscriber;
import com.web.listacompra.domain.userDomain.User;
import com.web.listacompra.herokuApplication.newRegistrationStreamHerokuApplication.NewRegistrationEvent;
import com.web.listacompra.herokuApplication.newRegistrationStreamHerokuApplication.NewRegistrationStreamEvent;
import com.web.listacompra.herokuApplication.newRegistrationStreamHerokuApplication.NewRegistrationStreamEventObserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping()
public class UserController {
    private final UserApplication userApplication;
    private NewRegistrationStreamEvent newRegistrationStreamEvent;
    @Autowired
    public UserController(final UserApplication userApplication, NewRegistrationStreamEvent newRegistrationStreamEvent){
        this.userApplication = userApplication;
        this.newRegistrationStreamEvent = newRegistrationStreamEvent;
    }
    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        path = UserControllerUrlPaths.USER_ADMIN_REGISTRATION_SERVICE
        )
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterUserOutDto registerUserAdmin(@Valid @RequestBody RegisterAdminDto registerAdminDto){
        return userApplication.registerAdmin(registerAdminDto);
    }
    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, 
        path = UserControllerUrlPaths.USER_REGISTRATION_SERVICE
        )
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody RegisterPossibleSubscriberDto registerPossibleSubscriberDto){
        userApplication.registerUser(registerPossibleSubscriberDto);
    }
    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        path = UserControllerUrlPaths.REGISTRATION_CONFIRMATION_SERVICE
        )
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterUserOutDto confirmUserRegistration(@Valid @RequestBody RegisterUserDto registerUserDto){
        return userApplication.confirmRegistration(registerUserDto);
    }
    @GetMapping(
        produces = MediaType.APPLICATION_NDJSON_VALUE,
        path = UserControllerUrlPaths.USER_REGISTRATION_ALERTER_SERVICE
        )
    public Flux<HttpBodyResponse> sendAdminTheCodeToConfirmRegistration(){
        /*Flux<HttpBodyResponse> httpBodyResponse = userApplication.notifyMeOfPossibleSubscribers()
            .map((PossibleSubscriberOutDto possibleSubscriberOutDto) -> {
                NewRegistrationEvent newRegistrationEvent = new NewRegistrationEvent();
                newRegistrationEvent.setNickname(possibleSubscriberOutDto.getNickName());
                newRegistrationEvent.setCode(possibleSubscriberOutDto.getCode());
                //empezar a contar
                return newRegistrationEvent;
            });
        Runnable sendControlMessage = () -> {
            ControlMessage controlMessage = new ControlMessage();
            controlMessage.setType("are you still connected");
            httpBodyResponse.mergeWith(Mono.just(controlMessage));
            System.out.println("executed");
        };
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.schedule(sendControlMessage, 3, TimeUnit.SECONDS);
        return httpBodyResponse;*/
        Flux<NewRegistrationEvent> newRegistrationEventFlux = Flux.<NewRegistrationEvent>create((FluxSink<NewRegistrationEvent> sink) -> {
            NewRegistrationStreamEventObserver observer = (NewRegistrationEvent event) -> {
                sink.next(event);
            };
            newRegistrationStreamEvent.subscribe(observer);
        });
        return newRegistrationEventFlux
        .map((NewRegistrationEvent event) -> {
            NewRegistration newRegistration = new NewRegistration();
            newRegistration.setNickname(event.getNickname());
            newRegistration.setCode(event.getCode());
            return newRegistration;
        });
    }
}
