package com.api.Controledeestacionamento.controllers;

import com.api.Controledeestacionamento.dtos.ControleDeEstacionamentoDto;
import com.api.Controledeestacionamento.models.ControleDeEstacionamentoModel;
import com.api.Controledeestacionamento.services.ControleDeEstacionamentoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/controleDeEstacionamento")
public class ControleDeEstacionamentoController {

    private final ControleDeEstacionamentoService controleDeEstacionamentoService;
    private static final Logger log = LoggerFactory.getLogger(ControleDeEstacionamentoController.class);

    public ControleDeEstacionamentoController(ControleDeEstacionamentoService controleDeEstacionamentoService) {
        this.controleDeEstacionamentoService = controleDeEstacionamentoService;
    }

    @PostMapping
    public ResponseEntity<Object> salvarControleDeEstacionamento(@RequestBody @Valid ControleDeEstacionamentoDto controleDeEstacionamentoDto) {
        log.info("criação da vaga iniciada.");
        return ResponseEntity.status(HttpStatus.CREATED).body(controleDeEstacionamentoService.save(controleDeEstacionamentoDto));
    }

    @GetMapping
    public ResponseEntity<List<ControleDeEstacionamentoModel>> getTodasVagasCadastradas() {
        log.info("todas as vagas cadastradas solicitadas.");
        return ResponseEntity.status(HttpStatus.OK).body(controleDeEstacionamentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ControleDeEstacionamentoModel> getUmaVaga(@PathVariable(value = "id") UUID id) {
        log.info("uma vaga solicitada.");
        return ResponseEntity.status(HttpStatus.OK).body(controleDeEstacionamentoService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVagaDeEstacionamento(@PathVariable(value = "id") UUID id) {
        log.info("excluindo vaga.");
        controleDeEstacionamentoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Vaga de estacionamento deletada com sucesso !");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVagaDeEstacionamento(@PathVariable(value = "id") UUID id, @RequestBody @Valid ControleDeEstacionamentoDto controleDeEstacionamentoDto) {
        log.info("atualizando vaga.");
        return ResponseEntity.status(HttpStatus.OK).body(controleDeEstacionamentoService.update(id, controleDeEstacionamentoDto));
    }

    @PatchMapping ("/{id}")
    public ResponseEntity<Object> patchVagaDeEstacionamento(@PathVariable(value = "id") UUID id, @RequestBody ControleDeEstacionamentoDto controleDeEstacionamentoDto) {
        log.info("realizando o patch vaga.");
        return ResponseEntity.status(HttpStatus.OK).body(controleDeEstacionamentoService.patch(id, controleDeEstacionamentoDto));
    }
}
