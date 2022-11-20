package com.web.listacompra.application.userApplication;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    @Size(max = 15, message = "Property nickName has exceeded the length limit of 15")
    private String nickName;
    @Max(value = 10000000, message = "Property code must be lower or equal to 10000000")
    @Min(value = 0, message = "Property code must be higher or equal to 0")
    private int code;
}
