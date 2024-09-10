package com.api.Controledeestacionamento.services;

import com.api.Controledeestacionamento.dtos.ControleDeEstacionamentoDto;
import com.api.Controledeestacionamento.models.ControleDeEstacionamentoModel;
import com.api.Controledeestacionamento.repositories.ControleDeEstacionamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.api.Controledeestacionamento.configs.CopyPropertiesConfig.copyProperties;

@Service
public class ControleDeEstacionamentoService {

    private final ControleDeEstacionamentoRepository controleDeEstacionamentoRepository;

    public ControleDeEstacionamentoService(ControleDeEstacionamentoRepository controleDeEstacionamentoRepository) {
        this.controleDeEstacionamentoRepository = controleDeEstacionamentoRepository;
    }

    @Transactional
    public ControleDeEstacionamentoModel save(ControleDeEstacionamentoDto controleDeEstacionamentoDto) {
        return Stream.of(convertControleEstacionamentoDTO(controleDeEstacionamentoDto))
                .map(controleDeEstacionamentoModel -> {
                    existePlacaDeCarro(controleDeEstacionamentoModel.getPlacaDoCarro());
                    existeVagaDeCarroEmUso(controleDeEstacionamentoModel.getVagaDoEstacionamento());
                    existeApartamentoBlocoEmUso(controleDeEstacionamentoModel.getApartamento(), controleDeEstacionamentoModel.getBloco());
                    return controleDeEstacionamentoRepository.save(controleDeEstacionamentoModel);
                })
                .findFirst()
                .orElseThrow();
    }

    public List<ControleDeEstacionamentoModel> findAll() {
        return controleDeEstacionamentoRepository.findAll();
    }

    public ControleDeEstacionamentoModel findById(UUID id) {
        return controleDeEstacionamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Vaga de estacionamento não encontrada !"));
    }

    @Transactional
    public void deleteById(UUID id) {
        Optional.of(findById(id)).ifPresent(controleDeEstacionamentoRepository::delete);
    }

    public ControleDeEstacionamentoModel update(UUID id, ControleDeEstacionamentoDto controleDeEstacionamentoDto) {
        ControleDeEstacionamentoModel controleDeEstacionamentoExistente = findById(id);
        ControleDeEstacionamentoModel controleDeEstacionamentoParaAtualizar = convertControleEstacionamentoDTO(controleDeEstacionamentoDto);

        copyProperties(controleDeEstacionamentoParaAtualizar, controleDeEstacionamentoExistente);
        return controleDeEstacionamentoRepository.save(controleDeEstacionamentoExistente);
    }

    private void existePlacaDeCarro(String placaDoCarro) {
        if (controleDeEstacionamentoRepository.existsByPlacaDoCarro(placaDoCarro)) {
            throw new RuntimeException("Conflito: Já existe um carro com essa placa !");
        }
    }

    private void existeVagaDeCarroEmUso(Integer vagaDoEstacionamento) {
        if (controleDeEstacionamentoRepository.existsByVagaDoEstacionamento(vagaDoEstacionamento)) {
            throw new RuntimeException("Conflito: A vaga esta em uso !");
        }
    }

    private void existeApartamentoBlocoEmUso(String apartamento, String bloco) {
        if (controleDeEstacionamentoRepository.existsByApartamentoAndBloco(apartamento, bloco)) {
            throw new RuntimeException("Conflito: A vaga já esta em uso pelo apartamento: " + apartamento + " e bloco: " + bloco);
        }
    }

    private ControleDeEstacionamentoModel convertControleEstacionamentoDTO(ControleDeEstacionamentoDto DTO) {
        return new ControleDeEstacionamentoModel(DTO.vagaDoEstacionamento(), DTO.placaDoCarro(), DTO.marcaDoCarro(), DTO.modeloDoCarro(), DTO.corDoCarro(), DTO.dataDeRegistro(), DTO.nomeDoResponsavel(), DTO.apartamento(), DTO.bloco());
    }


}
