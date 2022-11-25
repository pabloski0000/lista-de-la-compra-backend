package com.web.listacompra.herokuApplication.mocks;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.web.listacompra.application.userApplication.PossibleSubscriberOutDto;
import com.web.listacompra.application.userApplication.RegisterAdminDto;
import com.web.listacompra.application.userApplication.RegisterPossibleSubscriberDto;
import com.web.listacompra.application.userApplication.RegisterUserDto;
import com.web.listacompra.application.userApplication.RegisterUserOutDto;
import com.web.listacompra.application.userApplication.UserApplication;

import reactor.core.publisher.Flux;

public class UserApplicationMock implements UserApplication {

    @Override
    public RegisterUserOutDto registerAdmin(RegisterAdminDto registerAdminDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void registerUser(RegisterPossibleSubscriberDto registerPossibleSubscriberDto) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public RegisterUserOutDto confirmRegistration(RegisterUserDto registerUserDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Flux<PossibleSubscriberOutDto> notifyMeOfPossibleSubscribers() {
        List<PossibleSubscriberOutDto> possibleSubscriberOutDtoList = new ArrayList<>();
        PossibleSubscriberOutDto juan = new PossibleSubscriberOutDto();
        juan.setNickName("Juan");
        juan.setCode(1234);
        PossibleSubscriberOutDto alberto = new PossibleSubscriberOutDto();
        alberto.setNickName("Alberto");
        alberto.setCode(0000);
        PossibleSubscriberOutDto alejandro = new PossibleSubscriberOutDto();
        alejandro.setNickName("Alejandro");
        alejandro.setCode(9999);
        possibleSubscriberOutDtoList.add(juan);
        possibleSubscriberOutDtoList.add(alberto);
        possibleSubscriberOutDtoList.add(alejandro);
        return Flux.fromIterable(possibleSubscriberOutDtoList)
            .delayElements(Duration.ofSeconds(10));
    }

    @Override
    public void deleteMyself() {
        // TODO Auto-generated method stub
        
    }
    
}
