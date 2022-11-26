package com.web.listacompra.domain.userDomain;

import java.util.List;

import com.web.listacompra.domain.EntityBase;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("users")
public class User extends EntityBase {
    @Indexed(unique = true)
    private String nickName;
    private String accessToken;
    private List<String> roles;
}
