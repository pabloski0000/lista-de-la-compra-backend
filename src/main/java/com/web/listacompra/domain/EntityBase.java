package com.web.listacompra.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EntityBase implements Persistable<String> {
    @Id
    private String id;
    @Transient
    private boolean isNew = false;
}
