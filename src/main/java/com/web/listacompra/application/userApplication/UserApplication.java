package com.web.listacompra.application.userApplication;

import java.util.List;

import org.springframework.data.mongodb.core.ChangeStreamEvent;

import com.web.listacompra.domain.possibleSubscriberDomain.PossibleSubscriber;
import com.web.listacompra.domain.userDomain.User;
import com.web.listacompra.domain.userDomain.UserRepository;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserApplication {
    RegisterUserOutDto registerAdmin(RegisterAdminDto registerAdminDto);
    void registerUser(RegisterPossibleSubscriberDto registerPossibleSubscriberDto);
    RegisterUserOutDto confirmRegistration(RegisterUserDto registerUserDto);
    Flux<PossibleSubscriberOutDto> notifyMeOfPossibleSubscribers();
    void deleteMyself();
}
