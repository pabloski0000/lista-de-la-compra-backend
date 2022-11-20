package com.web.listacompra.application.sharedShoppingListApplication;

import com.web.listacompra.domain.SharedShoppingList;

public class AddItemUseCaseImpl implements AddItemUseCase {
    private SharedShoppingList sharedShoppingList;

    public AddItemUseCaseImpl(SharedShoppingList sharedShoppingList){
        this.sharedShoppingList = sharedShoppingList;
    }

    @Override
    public void addItem(CreateItemDto createItemDto) {
        sharedShoppingList.addItem(createItemDto.itemContent);
    }
}
