package com.api.Controledeestacionamento.mocks;

import com.api.Controledeestacionamento.dtos.ControleDeEstacionamentoDto;

import java.time.LocalDateTime;

public abstract class MocksDTO {

    public static ControleDeEstacionamentoDto controleDeEstacionamentoDtoResponseMock(){
        return new ControleDeEstacionamentoDto(1,
                "hkc4545",
                "ferrari",
                "F40",
                "Vermelho",
                LocalDateTime.parse("2021-01-01T18:30:00"),
                "Luiz",
                "34",
                "A12");
    }

    public static ControleDeEstacionamentoDto controleDeEstacionamentoDtoNullMock(){
        return new ControleDeEstacionamentoDto(null,
                "",
                "ferrari",
                "F40",
                "Vermelho",
                LocalDateTime.parse("2021-01-01T18:30:00"),
                "",
                "34",
                "A12");
    }

}
