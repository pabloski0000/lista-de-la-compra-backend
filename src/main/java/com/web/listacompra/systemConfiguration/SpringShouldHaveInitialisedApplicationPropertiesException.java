package com.web.listacompra.systemConfiguration;

import javax.xml.crypto.NoSuchMechanismException;

public class SpringShouldHaveInitialisedApplicationPropertiesException extends NoSuchMechanismException {
    public SpringShouldHaveInitialisedApplicationPropertiesException(String message){
        super(message);
    }
}
