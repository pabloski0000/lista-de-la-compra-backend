package com.web.listacompra.domain;

import java.util.List;

public interface SharedShoppingList {
    void addItem(String itemContent);
    void modifyItem(String oldItemContent, String newItemContent);
    void deleteItem(String itemContent);
    List<String> getContentOfAllItems();
}
