package com.api.Controledeestacionamento.controllers;

import com.api.Controledeestacionamento.dtos.ControleDeEstacionamentoDto;
import com.api.Controledeestacionamento.models.ControleDeEstacionamentoModel;
import com.api.Controledeestacionamento.services.ControleDeEstacionamentoService;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
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
        if (controleDeEstacionamentoService.existePlacaDeCarro(controleDeEstacionamentoDto.getPlacaDoCarro())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Já existe um carro com essa placa !");
        }
        if (controleDeEstacionamentoService.existeVagaDeCarroEmUso(controleDeEstacionamentoDto.getNumeroDoControleDeEstacionamento())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: A vaga esta em uso !");
        }
        if (controleDeEstacionamentoService.existeApartamentoBlocoEmUso(controleDeEstacionamentoDto.getApartamento(), controleDeEstacionamentoDto.getBloco())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: A vaga já esta em uso pelo apartamento:  "
                    + controleDeEstacionamentoDto.getApartamento() + "E bloco: " + controleDeEstacionamentoDto.getBloco());
        }
        ControleDeEstacionamentoModel controleDeEstacionamentoModel = new ControleDeEstacionamentoModel();
        BeanUtils.copyProperties(controleDeEstacionamentoDto, controleDeEstacionamentoModel);
        controleDeEstacionamentoModel.setDataDeRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(controleDeEstacionamentoService.save(controleDeEstacionamentoModel));
    }

    @GetMapping
    public ResponseEntity<List<ControleDeEstacionamentoModel>> getTodasVagasCadastradas() {
        return ResponseEntity.status(HttpStatus.OK).body(controleDeEstacionamentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUmaVaga(@PathVariable(value = "id") UUID id) {
        Optional<ControleDeEstacionamentoModel> controleDeEstacionamentoModelOptional = controleDeEstacionamentoService.findById(id);
        return !controleDeEstacionamentoModelOptional.isPresent() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamento não encontrada !")
                : ResponseEntity.status(HttpStatus.OK).body(controleDeEstacionamentoModelOptional.get());
    }


}
