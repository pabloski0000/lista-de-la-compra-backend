package com.web.listacompra.domain.exceptions;

import org.junit.jupiter.api.Test;

public class SharedShoppingListItemAlreadyExistsInListExceptionTest {
    private SharedShoppingListItemAlreadyExistsInListException sharedShoppingListItemAlreadyExistsInListException;
    private final static String DEFAULT_EXCEPTION_MESSAGE = "Something went wrong";
    @Test
    public void ItAcceptsTheExceptionMessageAsAnArgumentInTheConstructor(){
        sharedShoppingListItemAlreadyExistsInListException = new SharedShoppingListItemAlreadyExistsInListException(DEFAULT_EXCEPTION_MESSAGE);
    }
}
