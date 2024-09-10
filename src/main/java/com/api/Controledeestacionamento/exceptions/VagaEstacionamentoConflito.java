package com.api.Controledeestacionamento.exceptions;

public class VagaEstacionamentoConflito extends RuntimeException {

    public VagaEstacionamentoConflito() {
    }

    public VagaEstacionamentoConflito(String message) {
        super(message);
    }
}
