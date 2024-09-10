package com.api.Controledeestacionamento.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class VagaEstacionamentoNaoEncontrada  extends EntityNotFoundException {

    public VagaEstacionamentoNaoEncontrada() {
    }

    public VagaEstacionamentoNaoEncontrada(String message) {
        super(message);
    }
}
