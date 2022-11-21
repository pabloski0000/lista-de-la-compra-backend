package com.web.listacompra.controller.userController;

import java.util.List;

import javax.validation.Valid;

import com.web.listacompra.application.userApplication.PossibleSubscriberOutDto;
import com.web.listacompra.application.userApplication.RegisterAdminDto;
import com.web.listacompra.application.userApplication.RegisterPossibleSubscriberDto;
import com.web.listacompra.application.userApplication.RegisterUserDto;
import com.web.listacompra.application.userApplication.RegisterUserOutDto;
import com.web.listacompra.application.userApplication.UserApplication;
import com.web.listacompra.domain.possibleSubscriberDomain.PossibleSubscriber;
import com.web.listacompra.domain.userDomain.User;

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
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserApplication userApplication;
    @Autowired
    public UserController(final UserApplication userApplication){
        this.userApplication = userApplication;
    }
    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
        path = "/register-user-admin"
        )
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterUserOutDto registerAdmin(@Valid @RequestBody RegisterAdminDto registerAdminDto){
        return userApplication.registerAdmin(registerAdminDto);
    }
    @PostMapping(
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, 
        path = "/register-user"
        )
    @ResponseStatus(HttpStatus.CREATED)
    public void registerPossibleSubscriber(@Valid @RequestBody RegisterPossibleSubscriberDto registerPossibleSubscriberDto){
        userApplication.registerPossibleSubscriber(registerPossibleSubscriberDto);
    }
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, path = "/confirm-registration")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterUserOutDto registerUser(@Valid @RequestBody RegisterUserDto registerUserDto){
        return userApplication.registerUser(registerUserDto);
    }
    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE, path = "/notify-admin-of-new-registrations")
    public Flux<PossibleSubscriberOutDto> notifyMeOfPossibleSubscribers(){
        return userApplication.notifyMeOfPossibleSubscribers();
    }

}
