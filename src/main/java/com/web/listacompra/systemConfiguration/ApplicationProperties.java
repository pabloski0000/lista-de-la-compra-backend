package com.web.listacompra.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {
    private static ApplicationProperties singleton;
    private static final String SECRET_KEY_PARAMETER = "secretkey";
    private String secretKeyValue;
    private static final String EXCEPTION_MESSAGE =
        "ApplicationProperties class is prepared to be previously instatiated by spring before any user uses it";
    @Autowired
    private ApplicationProperties(Environment environment){
        secretKeyValue = environment.getProperty(SECRET_KEY_PARAMETER);
        singleton = this;
    }
    public static ApplicationProperties getSingleton(){
        if(singleton == null){
            throw new SpringShouldHaveInitialisedApplicationPropertiesException(EXCEPTION_MESSAGE);
        }
        return singleton;
    }
    public String getSecretKey(){
        return secretKeyValue;
    }
}
