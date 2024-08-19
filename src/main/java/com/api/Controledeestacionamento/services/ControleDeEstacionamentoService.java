package com.api.Controledeestacionamento.services;

import com.api.Controledeestacionamento.dtos.ControleDeEstacionamentoDto;
import com.api.Controledeestacionamento.models.ControleDeEstacionamentoModel;
import com.api.Controledeestacionamento.repositories.ControleDeEstacionamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ControleDeEstacionamentoService {

    final ControleDeEstacionamentoRepository controleDeEstacionamentoRepository;

    public ControleDeEstacionamentoService(ControleDeEstacionamentoRepository controleDeEstacionamentoRepository) {
        this.controleDeEstacionamentoRepository = controleDeEstacionamentoRepository;
    }

    @Transactional
    public ControleDeEstacionamentoModel save(ControleDeEstacionamentoDto controleDeEstacionamentoDto) {
        return Stream.of(convertControleEstacionamentoDTO(controleDeEstacionamentoDto))
                .map(controleDeEstacionamentoModel -> {
                    if (existePlacaDeCarro(controleDeEstacionamentoModel.getPlacaDoCarro())) {
                        throw new RuntimeException("Conflito: Já existe um carro com essa placa !");
                    }
                    if (existeVagaDeCarroEmUso(controleDeEstacionamentoModel.getNumeroDoControleDeEstacionamento())) {
                        throw new RuntimeException("Conflito: A vaga esta em uso !");
                    }
                    if (existeApartamentoBlocoEmUso(controleDeEstacionamentoModel.getApartamento(), controleDeEstacionamentoModel.getBloco())) {
                        throw new RuntimeException("Conflito: A vaga já esta em uso pelo apartamento:  " + controleDeEstacionamentoModel.getApartamento() + "E bloco: " + controleDeEstacionamentoModel.getBloco());
                    }
                    return controleDeEstacionamentoRepository.save(controleDeEstacionamentoModel);
                })
                .findFirst()
                .orElseThrow();
    }

    public List<ControleDeEstacionamentoModel> findAll() {
        return controleDeEstacionamentoRepository.findAll();
    }

    public Optional<ControleDeEstacionamentoModel> findById(UUID id) {
        return Optional.ofNullable(controleDeEstacionamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Vaga de estacionamento não encontrada !")));
    }

    @Transactional
    public void deleteById(UUID id) {
        findById(id).ifPresent(controleDeEstacionamentoRepository::delete);
    }

    public ControleDeEstacionamentoModel update(UUID id, ControleDeEstacionamentoDto controleDeEstacionamentoDto) {
        Optional<ControleDeEstacionamentoModel> controleDeEstacionamentoModelOptional = findById(id);
        BeanUtils.copyProperties(convertControleEstacionamentoDTO(controleDeEstacionamentoDto), controleDeEstacionamentoModelOptional.get());
        return controleDeEstacionamentoRepository.save(controleDeEstacionamentoModelOptional.get());
    }

    private boolean existePlacaDeCarro(String placaDoCarro) {
        return controleDeEstacionamentoRepository.existsByPlacaDoCarro(placaDoCarro);
    }

    private boolean existeVagaDeCarroEmUso(Integer numeroDoControleDeEstacionamento) {
        return controleDeEstacionamentoRepository.existsByNumeroDoControleDeEstacionamento(numeroDoControleDeEstacionamento);
    }

    private boolean existeApartamentoBlocoEmUso(String apartamento, String bloco) {
        return controleDeEstacionamentoRepository.existsByApartamentoAndBloco(apartamento, bloco);
    }

    private ControleDeEstacionamentoModel convertControleEstacionamentoDTO(ControleDeEstacionamentoDto DTO) {
        return new ControleDeEstacionamentoModel(DTO.numeroDoControleDeEstacionamento(), DTO.placaDoCarro(), DTO.marcaDoCarro(), DTO.modeloDoCarro(), DTO.corDoCarro(), DTO.dataDeRegistro(), DTO.nomeDoResponsavel(), DTO.apartamento(), DTO.bloco());
    }


}
