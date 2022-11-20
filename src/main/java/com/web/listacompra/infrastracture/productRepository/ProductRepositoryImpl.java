package com.web.listacompra.infrastracture.productRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.web.listacompra.domain.productDomain.Product;
import com.web.listacompra.domain.productDomain.ProductRepository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductConnectionMongoDBRepository productConnectionMongoDBRepository;
    private final MongoTemplate mongoTemplate;
    @Autowired
    public ProductRepositoryImpl(final ProductConnectionMongoDBRepository productConnectionMongoDBRepository, final MongoTemplate mongoTemplate){
        this.productConnectionMongoDBRepository = productConnectionMongoDBRepository;
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public List<Product> getAllProducts() {
        return productConnectionMongoDBRepository.findAll();
    }
    @Override
    public Optional<Product> addProduct(Product product) {
        product.setNew(true);
        Product storedProduct = productConnectionMongoDBRepository.save(product);
        storedProduct.setNew(false);
        return Optional.of(storedProduct);
    }
    @Override
    public Optional<Product> updateProduct(Product product) {
        product.setNew(false);
        Product updatedProduct = productConnectionMongoDBRepository.save(product);
        updatedProduct.setNew(false);
        return Optional.of(updatedProduct);
    }
    @Override
    public void deleteProductById(String id) {
        productConnectionMongoDBRepository.deleteById(id);
    }
    @Override
    public List<Product> deleteAllExcept(List<String> ids) {
        Query query = new Query(Criteria.where("id").nin(ids));
        mongoTemplate.remove(query, Product.class);
        return getAllProducts();
    }
    
}
