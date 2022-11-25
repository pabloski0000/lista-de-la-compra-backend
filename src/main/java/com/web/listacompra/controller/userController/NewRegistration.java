package com.web.listacompra.controller.userController;

public class NewRegistration implements HttpBodyResponse {
    private String nickname;
    private int code;
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
