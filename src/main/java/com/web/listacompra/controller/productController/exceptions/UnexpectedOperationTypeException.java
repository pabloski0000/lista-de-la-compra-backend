package com.web.listacompra.controller.productController.exceptions;

public class UnexpectedOperationTypeException extends IllegalArgumentException {
    public UnexpectedOperationTypeException(String message){
        super(message);
    }
}
