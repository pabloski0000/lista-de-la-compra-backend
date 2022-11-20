package com.web.listacompra.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.web.listacompra.core.exceptions.BadRequestException;

public class BadRequestExceptionTest {
    private BadRequestException badRequestException;
    @BeforeEach
    public void setUP(){
        badRequestException = new BadRequestException();
    }
    @Test
    public void returnInformationThatHasNotYetBeenSet(){
        String exceptionInformation = badRequestException.getInformation();
        assertEquals(exceptionInformation, Strings.EMPTY);
    }
    @Test
    public void noNullPassedAsArgument(){
        
    }
    @Test
    public void returnInformationPreviouslyPassedAsArgument(){
        String informationPassed = "Whatever message";
        badRequestException.setInformation(informationPassed);
        String informationReturned = badRequestException.getInformation();
        assertEquals(informationPassed, informationReturned);
    }
    @Test
    public void setInformation(){
        String informationAboutTheException = "information about the exception";
        badRequestException.setInformation(informationAboutTheException);
    }
}
