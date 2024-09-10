package com.api.Controledeestacionamento.responsePersonalized;

import java.io.Serializable;

public class respostaPersonalizada implements Serializable {

    private int statusCode;
    private Object message;

    public respostaPersonalizada() {
    }

    public respostaPersonalizada(int statusCode, Object message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getMessage() {
        return message;
    }
}
