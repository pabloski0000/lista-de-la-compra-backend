package com.web.listacompra.herokuApplication.newRegistrationStreamHerokuApplication;

public interface ObservableClock {
    void notifyWhenTimeElapses(ClockObserver clockObserver, int seconds);
}
