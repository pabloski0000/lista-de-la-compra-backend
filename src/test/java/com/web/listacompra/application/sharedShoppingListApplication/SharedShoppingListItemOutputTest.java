package com.web.listacompra.application.sharedShoppingListApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SharedShoppingListItemOutputTest {
    private SharedShoppingListItemOutput sharedShoppingListItemOutput;
    @BeforeEach
    public void setUp(){
        sharedShoppingListItemOutput = new SharedShoppingListItemOutput();
    }
    @Test
    public void itConstructsWithoutArgumentsBecauseItIsADataStructure(){
        sharedShoppingListItemOutput = new SharedShoppingListItemOutput();
    }
    @Test
    public void itAcceptsStringForTextAttribute(){
        final String defaultText = "Strawberry";
        sharedShoppingListItemOutput.text = defaultText;
    }
    @Test
    public void itReturnsTheSameTextThatWeGaveIt(){
        final String defaultString = "Apple";
        sharedShoppingListItemOutput.text = defaultString;
        assertEquals(defaultString, sharedShoppingListItemOutput.text);
    }
}
