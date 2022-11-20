package com.web.listacompra.application.exceptions;

import java.util.NoSuchElementException;

public class NoSuchItemHasBeenReceivedException extends NoSuchElementException {
    public NoSuchItemHasBeenReceivedException(String message){
        super(message);
    }
}
