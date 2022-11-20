package com.web.listacompra.controller.productController.presenters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.stereotype.Component;

import com.mongodb.client.model.changestream.OperationType;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonNotification;
import com.web.listacompra.controller.productController.presenters.strategiesOfPresenters.DeleteOperationTypeStrategy;
import com.web.listacompra.controller.productController.presenters.strategiesOfPresenters.InsertOperationTypeStrategy;
import com.web.listacompra.controller.productController.presenters.strategiesOfPresenters.ReplaceOperationTypeStrategy;
import com.web.listacompra.domain.productDomain.Product;

import reactor.core.publisher.Flux;

@Component
public class FutureChangesPresenter {
    private InsertOperationTypeStrategy insertOperationTypeStrategy;
    private ReplaceOperationTypeStrategy replaceOperationTypeStrategy;
    private DeleteOperationTypeStrategy deleteOperationTypeStrategy;
    @Autowired
    public FutureChangesPresenter(
        InsertOperationTypeStrategy insertOperationTypeStrategy,
        ReplaceOperationTypeStrategy replaceOperationTypeStrategy,
        DeleteOperationTypeStrategy deleteOperationTypeStrategy
        ){
        this.insertOperationTypeStrategy = insertOperationTypeStrategy;
        this.replaceOperationTypeStrategy = replaceOperationTypeStrategy;
        this.deleteOperationTypeStrategy = deleteOperationTypeStrategy;
    }
    public Flux<FormattedNDJsonNotification> present(Flux<ChangeStreamEvent<Product>> changeEvent){
        return changeEvent
        .map((ChangeStreamEvent<Product> changeStreamEvent) -> {
            if(changeStreamEvent.getOperationType() == OperationType.INSERT){
                return insertOperationTypeStrategy.execute(changeStreamEvent);
            }if(changeStreamEvent.getOperationType() == OperationType.REPLACE){
                return replaceOperationTypeStrategy.execute(changeStreamEvent);
            }else{
                return deleteOperationTypeStrategy.execute(changeStreamEvent);
            }
        });
    }
}
