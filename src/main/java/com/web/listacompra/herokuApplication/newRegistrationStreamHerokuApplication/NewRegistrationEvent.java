package com.web.listacompra.herokuApplication.newRegistrationStreamHerokuApplication;

public class NewRegistrationEvent {
    private String nickname;
    private int code;
    public NewRegistrationEvent(String nickName, int code){
        this.nickname = nickName;
        this.code = code;
    }
    public String getNickname() {
        return nickname;
    }
    public int getCode() {
        return code;
    }
}
