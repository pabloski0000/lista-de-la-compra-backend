package com.web.listacompra.application.userApplication;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterPossibleSubscriberDto {
    @Size(max = 15, message = "Property nickName has exceeded the length limit of 15")
    private String nickName;
}
