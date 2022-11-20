package com.web.listacompra.domain.productDomain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.web.listacompra.domain.EntityBase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("products")
public class Product extends EntityBase {
    @Indexed(unique = true)
    private String name;
}
