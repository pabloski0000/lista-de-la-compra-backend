package com.web.listacompra.core.exceptions;

import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;

import io.netty.util.collection.IntObjectHashMap;

public class BadRequestException {
    private String information;
    public String getInformation(){
        if(information == null){
            return "";
        }
        return information;
    }
    public void setInformation(String information){
        this.information = information;
    }
}
