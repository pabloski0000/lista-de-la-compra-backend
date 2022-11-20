package com.web.listacompra.infrastracture.productRepository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.web.listacompra.domain.productDomain.Product;

public interface ProductConnectionMongoDBRepository extends MongoRepository<Product, String> {
    
}
