package com.api.Controledeestacionamento.configsTest;

import com.api.Controledeestacionamento.configs.CopyPropertiesConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CopyPropertiesConfigTest {

    @Test
    void copyPropertiesComNuloNaOrigem() {
        String origem = null;
        String alvo = "nome";

        // Act
        CopyPropertiesConfig.copyProperties(origem, alvo);
        // Assert
        Assertions.assertEquals("nome", alvo);
    }

    @Test
    void copyPropertiesComNuloNoAlvo() {
        String origem = "nome";
        String alvo = null;

        // Act
        CopyPropertiesConfig.copyProperties(origem, alvo);
        // Assert
        Assertions.assertEquals("nome", origem);
    }

    @Test
    void copyPropertiesSemNulos() {
        ObjetoDeTeste origem = new ObjetoDeTeste();
        origem.setNome("nome1");

        ObjetoDeTeste alvo = new ObjetoDeTeste();
        alvo.setNome("nome2");

        CopyPropertiesConfig.copyProperties(origem, alvo);

        Assertions.assertEquals(origem.getNome(), alvo.getNome());
    }

    @Test
    void copyPropertiesComNulos() {
        Object origem = null;
        Object alvo = null;

        CopyPropertiesConfig.copyProperties(origem, alvo);

        Assertions.assertNull(alvo);
    }

    @Test
    void copyPropertiesSemNuloNaOrigemEAlvo() {
        Object origem = new Object();
        Object alvo = new Object();

        Executable copyProperties = () -> CopyPropertiesConfig.copyProperties(origem, alvo);
        Assertions.assertDoesNotThrow(copyProperties);
    }

    @Test
    void construtorDeveSerAcessivel() {
        CopyPropertiesConfig copyPropertiesConfig = new CopyPropertiesConfig() {
        };

        Assertions.assertNotNull(copyPropertiesConfig);
    }

    static class ObjetoDeTeste {
        private String nome;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
    }

}
