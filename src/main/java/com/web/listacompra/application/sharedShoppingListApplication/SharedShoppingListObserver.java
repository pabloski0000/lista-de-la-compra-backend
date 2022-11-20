package com.web.listacompra.application.sharedShoppingListApplication;

import com.web.listacompra.application.productApplication.ProductDtoOut;

public interface SharedShoppingListObserver {
    void itemAdded(SharedShoppingListItemOutput sharedShoppingListItemOutput);
    void itemModified(SharedShoppingListItemOutput oldItem, SharedShoppingListItemOutput newItem);
    void itemDeleted(SharedShoppingListItemOutput sharedShoppingListItemOutput);
}
