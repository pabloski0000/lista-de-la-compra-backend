package com.web.listacompra.application.productApplication;

import java.util.List;

import org.springframework.data.mongodb.core.ChangeStreamEvent;

import com.web.listacompra.domain.productDomain.Product;

import reactor.core.publisher.Flux;

public interface ProductApplication {
    List<ProductDtoOut> getAllProducts();
    ProductDtoOut addProduct(ProductDto productDto);
    void updateProduct(String id, ProductDto productDto);
    void deleteProductById(String id);
    List<ProductDtoOut> deleteAllProductsExcept(ProductDtoDeleteExcept productDtoDeleteExcept);
    Flux<ChangeStreamEvent<Product>> startSynchronizationShoppingList();
}
