package com.web.listacompra.controller.idleConnectionTimeout;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class IdleConnection {
    @GetMapping(path = IdleConnectionUrlPaths.KEEP_CONNECTION_ALIVE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void keepConnectionAlive(){
        
    }
}
