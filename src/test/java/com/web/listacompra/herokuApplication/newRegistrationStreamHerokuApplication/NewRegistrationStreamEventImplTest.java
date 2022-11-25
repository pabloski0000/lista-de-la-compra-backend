package com.web.listacompra.herokuApplication.newRegistrationStreamHerokuApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.web.listacompra.herokuApplication.mocks.NewRegistrationStreamEventObserverMock;
import com.web.listacompra.herokuApplication.mocks.TimeToPreventIdleConnectionInServerMock;
import com.web.listacompra.herokuApplication.mocks.UserApplicationMock;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class NewRegistrationStreamEventImplTest {
    private NewRegistrationStreamEventImpl newRegistrationStreamEvent;
    @BeforeEach
    public void setUp(){
        newRegistrationStreamEvent = 
            new NewRegistrationStreamEventImpl(new UserApplicationMock(), new TimeToPreventIdleConnectionInServerMock());
    }
    @Test
    void itNotifiesWithConnectionControlEventSoPortsKeepsOpenInHerokuServer() {
        NewRegistrationStreamEventObserverMock mockObserver = new NewRegistrationStreamEventObserverMock();
        newRegistrationStreamEvent.subscribe(mockObserver);
        /*while(true){
            
        }*/
    }
}
