package com.api.Controledeestacionamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ControleDeEstacionamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleDeEstacionamentoApplication.class, args);
	}

}
