package com.web.listacompra.domain.possibleSubscriberDomain;

import com.web.listacompra.domain.EntityBase;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("possibleSubscribers")
public class PossibleSubscriber extends EntityBase {
    private String nickName;
    private int code;
}
