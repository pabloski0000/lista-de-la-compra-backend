package com.web.listacompra.herokuApplication.mocks;

import com.web.listacompra.herokuApplication.newRegistrationStreamHerokuApplication.TimeToPreventIdleConnectionInServer;

public class TimeToPreventIdleConnectionInServerMock implements TimeToPreventIdleConnectionInServer {
    private int seconds = 2;
    @Override
    public int getSeconds() {
        return seconds;
    }
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
