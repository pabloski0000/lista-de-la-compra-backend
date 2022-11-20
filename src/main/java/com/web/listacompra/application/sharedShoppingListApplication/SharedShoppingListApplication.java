package com.web.listacompra.application.sharedShoppingListApplication;

public interface SharedShoppingListApplication extends SharedShoppingListRepositoryObserver {
    void synchroniseWithSharedShoppingList(SharedShoppingListObserver observer);
}
