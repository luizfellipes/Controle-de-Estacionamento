package com.api.Controledeestacionamento.mocks;

import com.api.Controledeestacionamento.models.ControleDeEstacionamentoModel;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class MocksModel {

        public static ControleDeEstacionamentoModel controleDeEstacionamentoModelResponseMock(){
            return new ControleDeEstacionamentoModel(1,
                    "hkc4545",
                    "ferrari",
                    "F40",
                    "Vermelho",
                    LocalDateTime.parse("2021-01-01T18:30:00"),
                    "Luiz",
                    "34",
                    "A12");
        }

        public static ControleDeEstacionamentoModel controleDeEstacionamentoModelRequestMock(){
            return new ControleDeEstacionamentoModel(
                    UUID.fromString("18bcbfad-bd63-4386-ba43-46a1fe148534"),
                    1,
                    "hkc4545",
                    "ferrari",
                    "F40",
                    "Vermelho",
                    LocalDateTime.parse("2021-01-01T18:30:00"),
                    "Luiz",
                    "34",
                    "A12");
        }
}
