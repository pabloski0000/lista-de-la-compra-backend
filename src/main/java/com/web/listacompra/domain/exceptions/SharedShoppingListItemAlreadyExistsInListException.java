package com.web.listacompra.domain.exceptions;

public class SharedShoppingListItemAlreadyExistsInListException extends IllegalArgumentException {
    public SharedShoppingListItemAlreadyExistsInListException(String message){
        super(message);
    }
    
}
