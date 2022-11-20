package com.web.listacompra.application.productApplication;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    @Size(max = 50, message = "Property name has exceeded the length limit of 50")
    private String name;
}
