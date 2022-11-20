package com.web.listacompra.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.booleanThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.web.listacompra.domain.exceptions.SharedShoppingListItemAlreadyExistsInListException;

public class SharedShoppingListTest {
    private SharedShoppingListImpl sharedShoppingList;
    private final static String DEFAULT_SHARED_SHOPPING_LIST_ITEM_TEXT = "Apple";
    @BeforeEach
    public void setup(){
        List<String> sharedShoppingListItemList = new ArrayList<>();
        sharedShoppingList = new SharedShoppingListImpl(sharedShoppingListItemList);
    }
    @Test
    public void itMayBeInstantiatedWithEmptyListOfSharedShoppingListItem(){
        final List<String> sharedShoppingListItemList = Collections.emptyList();
        sharedShoppingList = new SharedShoppingListImpl(sharedShoppingListItemList);
    }
    @Test
    public void itThrows_SharedShoppingListItemAlreadyExistsInListException_WhenTryingToAddAnItemThatIsEqualsAnotherOneInTheList(){
        String firstItem = DEFAULT_SHARED_SHOPPING_LIST_ITEM_TEXT;
        String secondItem = DEFAULT_SHARED_SHOPPING_LIST_ITEM_TEXT;
        sharedShoppingList.addItem(firstItem);
        boolean throwsSharedShoppingListItemAlreadyExistsInListException = false;
        try {
            sharedShoppingList.addItem(secondItem);
        } catch (SharedShoppingListItemAlreadyExistsInListException e) {
            throwsSharedShoppingListItemAlreadyExistsInListException = true;
        }
        assertEquals(throwsSharedShoppingListItemAlreadyExistsInListException, true);
    }
}
