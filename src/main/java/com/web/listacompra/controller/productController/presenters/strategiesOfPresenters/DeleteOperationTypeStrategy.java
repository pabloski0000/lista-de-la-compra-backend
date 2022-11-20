package com.web.listacompra.controller.productController.presenters.strategiesOfPresenters;

import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.stereotype.Component;

import com.web.listacompra.controller.productController.TypeOfNotification;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonDeletedItemNotification;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonItem;
import com.web.listacompra.domain.productDomain.Product;

@Component
public class DeleteOperationTypeStrategy implements FutureChangesPresenterStrategy {
    public FormattedNDJsonDeletedItemNotification execute(ChangeStreamEvent<Product> strategyee){
        FormattedNDJsonDeletedItemNotification formattedNDJsonDeletedItemNotification = new FormattedNDJsonDeletedItemNotification();
        FormattedNDJsonItem formattedNDJsonItem = new FormattedNDJsonItem();
        String formattedTypeOfNotification = TypeOfNotification.DELETED_ITEM;
        formattedNDJsonDeletedItemNotification.setTypeOfNotification(formattedTypeOfNotification);
        String itemId = strategyee.getRaw().getDocumentKey().get("_id").asObjectId().getValue().toString();
        formattedNDJsonItem.setId(itemId);
        formattedNDJsonDeletedItemNotification.setItem(formattedNDJsonItem);
        return formattedNDJsonDeletedItemNotification;
    }
}
