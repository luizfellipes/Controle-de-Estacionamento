package com.api.Controledeestacionamento.exceptions;

public class VagaEstacionamentoBadRequest extends IllegalArgumentException{

    public VagaEstacionamentoBadRequest() {
    }

    public VagaEstacionamentoBadRequest(String message) {
        super(message);
    }
}
