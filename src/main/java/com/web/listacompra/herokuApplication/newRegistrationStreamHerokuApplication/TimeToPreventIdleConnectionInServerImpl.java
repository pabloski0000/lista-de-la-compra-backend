package com.web.listacompra.herokuApplication.newRegistrationStreamHerokuApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.web.listacompra.systemConfiguration.EnvironmentProperties;

@Component
public class TimeToPreventIdleConnectionInServerImpl implements TimeToPreventIdleConnectionInServer {
    private final int seconds;
    @Autowired
    public TimeToPreventIdleConnectionInServerImpl(EnvironmentProperties environmentProperties){
        seconds = environmentProperties.getSecondsToSendMessageToPreventIdleConnection();
    }
    @Override
    public int getSeconds() {
        return seconds;
    }
    
}
