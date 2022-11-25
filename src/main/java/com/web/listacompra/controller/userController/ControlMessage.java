package com.web.listacompra.controller.userController;

public class ControlMessage implements HttpBodyResponse {
    private String type;
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}
