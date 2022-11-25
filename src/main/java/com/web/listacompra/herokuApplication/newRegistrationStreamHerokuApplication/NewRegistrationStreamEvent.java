package com.web.listacompra.herokuApplication.newRegistrationStreamHerokuApplication;

public interface NewRegistrationStreamEvent {
    void subscribe(NewRegistrationStreamEventObserver observer);
}
