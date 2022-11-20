package com.web.listacompra.controller.productController;

import com.mongodb.client.model.changestream.OperationType;
import com.web.listacompra.application.productApplication.ProductApplication;
import com.web.listacompra.application.productApplication.ProductDto;
import com.web.listacompra.application.productApplication.ProductDtoDeleteExcept;
import com.web.listacompra.application.productApplication.ProductDtoOut;
import com.web.listacompra.controller.productController.exceptions.UnexpectedOperationTypeException;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonAddedItemNotification;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonCurrentStateOfListNotification;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonDeletedItemNotification;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonItem;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonModifiedItemNotification;
import com.web.listacompra.controller.productController.formattedClassesForNDJsonResponse.FormattedNDJsonNotification;
import com.web.listacompra.controller.productController.presenters.FutureChangesPresenter;
import com.web.listacompra.controller.productController.presenters.ListOfProductsPresenter;
import com.web.listacompra.domain.productDomain.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductApplication productApplication;
    private ListOfProductsPresenter currentStateOfListPresenter;
    private FutureChangesPresenter futureChangesPresenter;
    @Autowired
    public ProductController(final ProductApplication productApplication, ListOfProductsPresenter currentStateOfListPresenter,
    FutureChangesPresenter futureChangesPresenter){
        this.productApplication = productApplication;
        this.currentStateOfListPresenter = currentStateOfListPresenter;
        this.futureChangesPresenter = futureChangesPresenter;
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDtoOut> getAllProducts(){
        return productApplication.getAllProducts();
    }
    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE, path = "synchronise-with-shopping-list")
    public Flux<? extends FormattedNDJsonNotification> startSynchronizationShoppingList(){
        List<ProductDtoOut> listOfProducts = productApplication.getAllProducts();
        Flux<FormattedNDJsonCurrentStateOfListNotification> putClientUpToDate = currentStateOfListPresenter.present(listOfProducts);
        Flux<ChangeStreamEvent<Product>> subscribeClientForFutureChanges = productApplication.startSynchronizationShoppingList();
        Flux<FormattedNDJsonNotification> formattedFutureEvents = futureChangesPresenter.present(subscribeClientForFutureChanges);
        return Flux.concat(putClientUpToDate, formattedFutureEvents);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductDtoOut addProduct(@Valid @RequestBody ProductDto productDto){
        return productApplication.addProduct(productDto);
    }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable String id ,@Valid @RequestBody ProductDto productDto){
        productApplication.updateProduct(id, productDto);
    }
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String id){
        productApplication.deleteProductById(id);
    }
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductDtoOut> deleteAllProductsExcept(@Valid @RequestBody ProductDtoDeleteExcept productDtoDeleteExcept){
        return productApplication.deleteAllProductsExcept(productDtoDeleteExcept);
    }
}
