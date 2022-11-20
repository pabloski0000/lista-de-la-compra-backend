package com.web.listacompra.controller.productController.presenters.strategiesOfPresenters;

import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.stereotype.Component;

import com.web.listacompra.controller.productController.TypeOfNotification;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonAddedItemNotification;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonItem;
import com.web.listacompra.domain.productDomain.Product;

@Component
public class InsertOperationTypeStrategy implements FutureChangesPresenterStrategy {
    public FormattedNDJsonAddedItemNotification execute(ChangeStreamEvent<Product> strategyee){
        FormattedNDJsonAddedItemNotification formattedNDJsonAddedItemNotification = new FormattedNDJsonAddedItemNotification();
        FormattedNDJsonItem formattedNDJsonItem = new FormattedNDJsonItem();
        String formattedTypeOfNotification = TypeOfNotification.ADDED_ITEM;
        formattedNDJsonAddedItemNotification.setTypeOfNotification(formattedTypeOfNotification);
        String itemId = strategyee.getBody().getId();
        String itemText = strategyee.getBody().getName();
        formattedNDJsonItem.setId(itemId);
        formattedNDJsonItem.setText(itemText);
        formattedNDJsonAddedItemNotification.setItem(formattedNDJsonItem);
        return formattedNDJsonAddedItemNotification;
    }
}
