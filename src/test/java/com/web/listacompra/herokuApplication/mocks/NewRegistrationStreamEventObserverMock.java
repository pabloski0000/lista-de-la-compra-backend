package com.web.listacompra.herokuApplication.mocks;

import com.web.listacompra.herokuApplication.newRegistrationStreamHerokuApplication.NewRegistrationEvent;
import com.web.listacompra.herokuApplication.newRegistrationStreamHerokuApplication.NewRegistrationStreamEventObserver;

public class NewRegistrationStreamEventObserverMock implements NewRegistrationStreamEventObserver {
    private NewRegistrationEvent newRegistrationEvent;
    @Override
    public void notify(NewRegistrationEvent newRegistrationEvent) {
        this.newRegistrationEvent = newRegistrationEvent;
        System.out.println(newRegistrationEvent.getNickname());
        System.out.println(newRegistrationEvent.getCode());
    }
    public NewRegistrationEvent getNewRegistrationEvent() {
        return newRegistrationEvent;
    }
}
