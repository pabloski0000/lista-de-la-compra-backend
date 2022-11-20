package com.web.listacompra.application.mocks;

import com.web.listacompra.application.exceptions.NoSuchItemHasBeenReceivedException;
import com.web.listacompra.application.sharedShoppingListApplication.SharedShoppingListItemOutput;
import com.web.listacompra.application.sharedShoppingListApplication.SharedShoppingListObserver;

public class SharedShoppingListObserverMock implements SharedShoppingListObserver {
    private SharedShoppingListItemOutput itemAdded;
    private SharedShoppingListItemOutput oldItem;
    private SharedShoppingListItemOutput newItem;
    private SharedShoppingListItemOutput itemDeleted;

    @Override
    public void itemAdded(SharedShoppingListItemOutput sharedShoppingListItemOutput) {
        itemAdded = sharedShoppingListItemOutput;
        
    }

    @Override
    public void itemModified(SharedShoppingListItemOutput oldItem, SharedShoppingListItemOutput newItem) {
        this.oldItem = oldItem;
        this.newItem = newItem;
    }

    @Override
    public void itemDeleted(SharedShoppingListItemOutput sharedShoppingListItemOutput) {
        itemDeleted = sharedShoppingListItemOutput;
        
    }

    public SharedShoppingListItemOutput getItemAdded() {
        if(itemAdded == null){
            throw new NoSuchItemHasBeenReceivedException(beginningOfNoSuchItemHasBeenReceivedExceptionMessage() + " itemAdded property as null");
        }
        return itemAdded;
    }

    public SharedShoppingListItemOutput getOldItem() {
        if(oldItem == null){
            throw new NoSuchItemHasBeenReceivedException(beginningOfNoSuchItemHasBeenReceivedExceptionMessage() + " oldItem property as null");
        }
        return oldItem;
    }

    public SharedShoppingListItemOutput getNewItem() {
        if(newItem == null){
            throw new NoSuchItemHasBeenReceivedException(beginningOfNoSuchItemHasBeenReceivedExceptionMessage() + " newItem property as null");
        }
        return newItem;
    }

    public SharedShoppingListItemOutput getIteDeleted() {
        if(itemDeleted == null){
            throw new NoSuchItemHasBeenReceivedException(beginningOfNoSuchItemHasBeenReceivedExceptionMessage() + " itemDeleted property as null");
        }
        return itemDeleted;
    }
    private String beginningOfNoSuchItemHasBeenReceivedExceptionMessage(){
        return "This mock contains";
    }
}
