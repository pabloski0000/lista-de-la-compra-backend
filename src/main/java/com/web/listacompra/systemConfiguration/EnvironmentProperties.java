package com.web.listacompra.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentProperties {
    private static EnvironmentProperties singleton;
    private static final String SECRET_KEY_PARAMETER = "JWT_SECRET_KEY";
    private static final String SECONDS_TO_SEND_MESSAGE_TO_PREVENT_IDLE_CONNECTION_PARAMETER 
        = "SECONDS_TO_SEND_MESSAGE_TO_PREVENT_IDLE_CONNECTION";
    private String secretKeyValue;
    private int secondsToSendMessageToPreventIdleConnection;
    private static final String EXCEPTION_MESSAGE =
        "ApplicationProperties class is prepared to be previously instatiated by spring before any user uses it";
    @Autowired
    private EnvironmentProperties(Environment environment){
        secretKeyValue = environment.getProperty(SECRET_KEY_PARAMETER);
        secondsToSendMessageToPreventIdleConnection = 
            Integer.parseInt(environment.getProperty(SECONDS_TO_SEND_MESSAGE_TO_PREVENT_IDLE_CONNECTION_PARAMETER));
        singleton = this;
    }
    public static EnvironmentProperties getSingleton(){
        if(singleton == null){
            throw new SpringShouldHaveInitialisedApplicationPropertiesException(EXCEPTION_MESSAGE);
        }
        return singleton;
    }
    public String getSecretKey(){
        return secretKeyValue;
    }
    public int getSecondsToSendMessageToPreventIdleConnection() {
        return secondsToSendMessageToPreventIdleConnection;
    }
}
