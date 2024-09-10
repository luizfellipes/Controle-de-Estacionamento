package com.api.Controledeestacionamento.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;


public record ControleDeEstacionamentoDto(
        @NotNull
        Integer vagaDoEstacionamento,

        @NotBlank
        @Size(max = 7)
        String placaDoCarro,

        @NotBlank
        String marcaDoCarro,

        @NotBlank
        String modeloDoCarro,

        @NotBlank
        String corDoCarro,

        @NotNull
        LocalDateTime dataDeRegistro,

        @NotBlank
        String nomeDoResponsavel,

        @NotBlank
        String apartamento,

        @NotBlank
        String bloco
) {
}

