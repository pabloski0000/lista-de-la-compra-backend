package com.web.listacompra.herokuApplication.newRegistrationStreamHerokuApplication;

import org.springframework.stereotype.Component;

@Component
public class TimeToPreventIdleConnectionInServerImpl implements TimeToPreventIdleConnectionInServer {
    private final int seconds = 3;
    @Override
    public int getSeconds() {
        return seconds;
    }
    
}
