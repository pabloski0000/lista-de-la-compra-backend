package com.web.listacompra.application.sharedShoppingListApplication;

public interface SharedShoppingListRepositoryObserver {
    void itemAdded(SharedShoppingListItemOutput sharedShoppingListItemOutput);
    void itemModified(SharedShoppingListItemOutput oldItem, SharedShoppingListItemOutput newItem);
    void itemDeleted(SharedShoppingListItemOutput sharedShoppingListItemOutput);
}
