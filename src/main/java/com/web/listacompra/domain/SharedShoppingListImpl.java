package com.web.listacompra.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.web.listacompra.domain.exceptions.SharedShoppingListItemAlreadyExistsInListException;

public class SharedShoppingListImpl implements SharedShoppingList {
    private List<String> items;

    public SharedShoppingListImpl(List<String> items){
        this.items = items;
    }
    @Override
    public void addItem(String itemContent){
        if(items.contains(itemContent)){
            throw new SharedShoppingListItemAlreadyExistsInListException("The item passed as argument already exists"
            + "or has the same text as one that is already contained in the list");
        }
        items.add(itemContent);
    }
    @Override
    public void modifyItem(String oldItemContent, String newItemContent) {
        int indexOfOldItem = items.indexOf(oldItemContent);
        items.set(indexOfOldItem, newItemContent);
    }
    @Override
    public void deleteItem(String itemContent) {
        items.remove(itemContent);
    }
    @Override
    public List<String> getContentOfAllItems() {
        List<String> itemContentList = new ArrayList<>();
        Collections.copy(items, itemContentList);
        return itemContentList;
    }
}
