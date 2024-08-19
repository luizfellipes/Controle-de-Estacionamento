package com.api.Controledeestacionamento.controllers;

import com.api.Controledeestacionamento.dtos.ControleDeEstacionamentoDto;
import com.api.Controledeestacionamento.models.ControleDeEstacionamentoModel;
import com.api.Controledeestacionamento.services.ControleDeEstacionamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/controleDeEstacionamento")
public class ControleDeEstacionamentoController {
    final ControleDeEstacionamentoService controleDeEstacionamentoService;

    public ControleDeEstacionamentoController(ControleDeEstacionamentoService controleDeEstacionamentoService) {
        this.controleDeEstacionamentoService = controleDeEstacionamentoService;
    }

    @PostMapping
    public ResponseEntity<Object> salvarControleDeEstacionamento(@RequestBody @Valid ControleDeEstacionamentoDto controleDeEstacionamentoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(controleDeEstacionamentoService.save(controleDeEstacionamentoDto));
    }

    @GetMapping
    public ResponseEntity<List<ControleDeEstacionamentoModel>> getTodasVagasCadastradas() {
        return ResponseEntity.status(HttpStatus.OK).body(controleDeEstacionamentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUmaVaga(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(controleDeEstacionamentoService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVagaDeEstacionamento(@PathVariable(value = "id") UUID id) {
        controleDeEstacionamentoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Vaga de estacionamento deletada com sucesso !");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVagaDeEstacionamento(@PathVariable(value = "id") UUID id, @RequestBody @Valid ControleDeEstacionamentoDto controleDeEstacionamentoDto) {
         return ResponseEntity.status(HttpStatus.OK).body(controleDeEstacionamentoService.update(id, controleDeEstacionamentoDto));
    }
}
