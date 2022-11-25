package com.web.listacompra.herokuApplication.newRegistrationStreamHerokuApplication;

import java.util.Date;

public class ObservableClockImpl implements ObservableClock {
    private Date initialDate;
    @Override
    public void notifyWhenTimeElapses(ClockObserver clockObserver, int seconds) {
        initialDate = new Date();
        while(parseMillisecondsToSeconds(new Date().compareTo(initialDate)) > seconds){}
        clockObserver.timeElapsed();
    }
    private int parseMillisecondsToSeconds(long milliseconds){
        int seconds = (int) (milliseconds / 1000);
        return seconds;
    }
}
