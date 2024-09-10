package com.api.Controledeestacionamento.services;

import com.api.Controledeestacionamento.dtos.ControleDeEstacionamentoDto;
import com.api.Controledeestacionamento.exceptions.VagaEstacionamentoBadRequest;
import com.api.Controledeestacionamento.exceptions.VagaEstacionamentoConflito;
import com.api.Controledeestacionamento.exceptions.VagaEstacionamentoNaoEncontrada;
import com.api.Controledeestacionamento.models.ControleDeEstacionamentoModel;
import com.api.Controledeestacionamento.repositories.ControleDeEstacionamentoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.api.Controledeestacionamento.configs.CopyPropertiesConfig.copyProperties;

@Service
public class ControleDeEstacionamentoService {

    private final ControleDeEstacionamentoRepository controleDeEstacionamentoRepository;
    private static final Logger log = LoggerFactory.getLogger(ControleDeEstacionamentoService.class);

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
                    log.info("vaga registrada.");
                    return controleDeEstacionamentoRepository.save(controleDeEstacionamentoModel);
                })
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Não foi possivel registrar a vaga.");
                    return new VagaEstacionamentoBadRequest("Não é possivel registrar a vaga cadastrada.");
                });
    }

    public List<ControleDeEstacionamentoModel> findAll() {
        log.info("mostrando todas as vagas registradas.");
        return controleDeEstacionamentoRepository.findAll();
    }

    public ControleDeEstacionamentoModel findById(UUID id) {
        return controleDeEstacionamentoRepository.findById(id).orElseThrow(() -> {
            log.warn("Vaga de estacionamento não encontrada !");
            return new VagaEstacionamentoNaoEncontrada("Vaga de estacionamento não encontrada !");
        });
    }

    @Transactional
    public void deleteById(UUID id) {
        Optional.of(findById(id)).ifPresent(controleDeEstacionamentoRepository::delete);
        log.info("Vaga deletada com sucesso.");
    }

    public ControleDeEstacionamentoModel update(UUID id, ControleDeEstacionamentoDto controleDeEstacionamentoDto) {
        return Stream.of(convertControleEstacionamentoDTO(controleDeEstacionamentoDto))
                .map(controleDeEstacionamentoParaAtualizar -> {
                    ControleDeEstacionamentoModel controleDeEstacionamentoExistente = findById(id);
                    copyProperties(controleDeEstacionamentoParaAtualizar, controleDeEstacionamentoExistente);
                    log.info("Vaga atualizada.");
                    return controleDeEstacionamentoRepository.save(controleDeEstacionamentoExistente);
                })
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Não foi possivel realizar a atualização");
                    return new VagaEstacionamentoBadRequest("Não foi possivel realizar a atualizaçao da vaga");
                });
    }

    public ControleDeEstacionamentoModel patch(UUID id, ControleDeEstacionamentoDto controleDeEstacionamentoDto) {
        return Stream.of(convertControleEstacionamentoDTO(controleDeEstacionamentoDto))
                .map(controleDeEstacionamentoParaAtualizar -> {
                    ControleDeEstacionamentoModel controleDeEstacionamentoExistente = findById(id);
                    copyProperties(controleDeEstacionamentoParaAtualizar, controleDeEstacionamentoExistente);
                    log.info("Vaga atualizada por patch.");
                    return controleDeEstacionamentoRepository.save(controleDeEstacionamentoExistente);
                })
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Não foi possivel realizar a atualização do patch");
                    return new VagaEstacionamentoBadRequest("Não foi possivel realizar a atualizaçao da vaga por patch");
                });
    }

    private void existePlacaDeCarro(String placaDoCarro) {
        if (controleDeEstacionamentoRepository.existsByPlacaDoCarro(placaDoCarro)) {
            log.warn("placa de carro existente.");
            throw new VagaEstacionamentoConflito("Conflito: Já existe um carro com essa placa !");
        }
    }

    private void existeVagaDeCarroEmUso(Integer vagaDoEstacionamento) {
        if (controleDeEstacionamentoRepository.existsByVagaDoEstacionamento(vagaDoEstacionamento)) {
            log.warn("vaga existente.");
            throw new VagaEstacionamentoConflito("Conflito: A vaga esta em uso !");
        }
    }

    private void existeApartamentoBlocoEmUso(String apartamento, String bloco) {
        if (controleDeEstacionamentoRepository.existsByApartamentoAndBloco(apartamento, bloco)) {
            log.warn("vaga já esta em uso pelo apartamento");
            throw new VagaEstacionamentoConflito("Conflito: A vaga já esta em uso pelo apartamento: " + apartamento + " e bloco: " + bloco);
        }
    }

    private ControleDeEstacionamentoModel convertControleEstacionamentoDTO(ControleDeEstacionamentoDto DTO) {
        return new ControleDeEstacionamentoModel(DTO.vagaDoEstacionamento(), DTO.placaDoCarro(), DTO.marcaDoCarro(), DTO.modeloDoCarro(), DTO.corDoCarro(), DTO.dataDeRegistro(), DTO.nomeDoResponsavel(), DTO.apartamento(), DTO.bloco());
    }


}
