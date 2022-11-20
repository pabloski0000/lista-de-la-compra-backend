package com.web.listacompra.controller.productController.presenters.strategiesOfPresenters;

import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.stereotype.Component;

import com.web.listacompra.controller.productController.TypeOfNotification;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonItem;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonModifiedItemNotification;
import com.web.listacompra.domain.productDomain.Product;

@Component
public class ReplaceOperationTypeStrategy implements FutureChangesPresenterStrategy {
    public FormattedNDJsonModifiedItemNotification execute(ChangeStreamEvent<Product> strategyee){
        FormattedNDJsonModifiedItemNotification formattedNDJsonModifiedItemNotification = new FormattedNDJsonModifiedItemNotification();
        FormattedNDJsonItem formattedNDJsonItem = new FormattedNDJsonItem();
        String formattedTypeOfNotification = TypeOfNotification.MODIFIED_ITEM;
        formattedNDJsonModifiedItemNotification.setTypeOfNotification(formattedTypeOfNotification);
        String itemId = strategyee.getBody().getId();
        String itemText = strategyee.getBody().getName();
        formattedNDJsonItem.setId(itemId);
        formattedNDJsonItem.setText(itemText);
        formattedNDJsonModifiedItemNotification.setItem(formattedNDJsonItem);
        return formattedNDJsonModifiedItemNotification;
    }
}
