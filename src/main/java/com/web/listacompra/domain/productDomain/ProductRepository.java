package com.web.listacompra.domain.productDomain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getAllProducts();
    Optional<Product> addProduct(Product product);
    Optional<Product> updateProduct(Product product);
    void deleteProductById(String id);
    List<Product> deleteAllExcept(List<String> ids);
}
