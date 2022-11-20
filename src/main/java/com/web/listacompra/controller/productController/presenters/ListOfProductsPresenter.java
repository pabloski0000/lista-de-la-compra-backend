package com.web.listacompra.controller.productController.presenters;

import java.util.List;

import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.stereotype.Component;

import com.web.listacompra.application.productApplication.ProductDtoOut;
import com.web.listacompra.controller.productController.TypeOfNotification;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonAddedItemNotification;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonCurrentStateOfListNotification;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonItem;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonModifiedItemNotification;
import com.web.listacompra.domain.productDomain.Product;

import reactor.core.publisher.Flux;

@Component
public class ListOfProductsPresenter {
    public Flux<FormattedNDJsonCurrentStateOfListNotification> present(List<ProductDtoOut> rawData) {
        return Flux.just(rawData.toArray(new ProductDtoOut[0]))
        .map((ProductDtoOut productDtoOut) -> {
            FormattedNDJsonCurrentStateOfListNotification currentStateOfListClientNotification = new FormattedNDJsonCurrentStateOfListNotification();
            FormattedNDJsonItem formattedNDJsonItem = new FormattedNDJsonItem();
            String formattedTypeOfNotification = TypeOfNotification.CURRENT_STATE;
            currentStateOfListClientNotification.setTypeOfNotification(formattedTypeOfNotification);
            String itemId = productDtoOut.getId();
            String itemText = productDtoOut.getName();
            formattedNDJsonItem.setId(itemId);
            formattedNDJsonItem.setText(itemText);
            currentStateOfListClientNotification.setItem(formattedNDJsonItem);
            return currentStateOfListClientNotification;
        });
    }
}
