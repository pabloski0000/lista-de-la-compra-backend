package com.web.listacompra.application.productApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.ReactiveChangeStreamOperation.ChangeStreamWithFilterAndProjection;
import org.springframework.stereotype.Service;

import com.web.listacompra.domain.productDomain.Product;
import com.web.listacompra.domain.productDomain.ProductRepository;

import reactor.core.publisher.Flux;

@Service
public class ProductApplicationImpl implements ProductApplication {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    @Autowired
    public ProductApplicationImpl(final ProductRepository productRepository, final ModelMapper modelMapper, final ReactiveMongoTemplate reactiveMongoTemplate){
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }
    @Override
    public List<ProductDtoOut> getAllProducts() {
        List<Product> products = productRepository.getAllProducts();
        List<ProductDtoOut> productDtoOuts = new ArrayList<>();
        for(Product product : products){
            productDtoOuts.add(modelMapper.map(product, ProductDtoOut.class));
        }
        return productDtoOuts;
    }
    @Override
    public ProductDtoOut addProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        productRepository.addProduct(product);
        return modelMapper.map(product, ProductDtoOut.class);
    }
    @Override
    public void updateProduct(String id, ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        product.setId(id);
        productRepository.updateProduct(product);
    }
    @Override
    public void deleteProductById(String id) {
        productRepository.deleteProductById(id);
    }
    @Override
    public List<ProductDtoOut> deleteAllProductsExcept(ProductDtoDeleteExcept productDtoDeleteExcepts) {
        List<ProductDtoOut> productDtoOuts = new ArrayList<>();
        List<String> ids = productDtoDeleteExcepts.getIds();
        for(Product product : productRepository.deleteAllExcept(ids)){
            productDtoOuts.add(modelMapper.map(product, ProductDtoOut.class));
        }
        return productDtoOuts;
    }
    @Override
    public Flux<ChangeStreamEvent<Product>> startSynchronizationShoppingList() {
        return reactiveMongoTemplate.changeStream(Product.class)
            .watchCollection(Product.class)
            .listen();
    }
    
    public Flux<?> tryingOut() {
        ChangeStreamWithFilterAndProjection<Product> changeStreamWithFilterAndProjection = reactiveMongoTemplate.changeStream(Product.class)
        .watchCollection(Product.class);
        List<ProductDtoOut> products = getAllProducts();
        return Flux.<ProductDtoOut>fromStream(products.stream())
        .thenMany(
            changeStreamWithFilterAndProjection.listen()
        );
    }
}
