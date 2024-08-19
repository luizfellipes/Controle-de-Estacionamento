package com.api.Controledeestacionamento.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record ControleDeEstacionamentoDto(
        @NotBlank
        String numeroDoControleDeEstacionamento,
        @NotBlank
        @Size(max = 7)
        String placaDoCarro,
        @NotBlank
        String marcaDoCarro,
        @NotBlank
        String modeloDoCarro,
        @NotBlank
        String corDoCarro,
        @NotBlank
        String nomeDoResponsavel,
        @NotBlank
        String apartamento,
        @NotBlank
        String bloco
) {
}

