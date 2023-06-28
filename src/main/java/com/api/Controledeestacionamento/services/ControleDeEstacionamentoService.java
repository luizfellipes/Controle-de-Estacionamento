package com.api.Controledeestacionamento.services;

import com.api.Controledeestacionamento.models.ControleDeEstacionamentoModel;
import com.api.Controledeestacionamento.repositories.ControleDeEstacionamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ControleDeEstacionamentoService {

    final ControleDeEstacionamentoRepository controleDeEstacionamentoRepository;

    public ControleDeEstacionamentoService(ControleDeEstacionamentoRepository controleDeEstacionamentoRepository) {
        this.controleDeEstacionamentoRepository = controleDeEstacionamentoRepository;
    }

    @Transactional
    public ControleDeEstacionamentoModel save(ControleDeEstacionamentoModel controleDeEstacionamentoModel) {
        return controleDeEstacionamentoRepository.save(controleDeEstacionamentoModel);
    }

    public List<ControleDeEstacionamentoModel> findAll() {
        return controleDeEstacionamentoRepository.findAll();
    }

    public Optional<ControleDeEstacionamentoModel> findById(UUID id) {
        return controleDeEstacionamentoRepository.findById(id);
    }

    public boolean existePlacaDeCarro(String placaDoCarro) {
        return controleDeEstacionamentoRepository.existsByPlacaDoCarro(placaDoCarro);
    }

    public boolean existeVagaDeCarroEmUso(String numeroDoControleDeEstacionamento) {
        return controleDeEstacionamentoRepository.existsByNumeroDoControleDeEstacionamento(numeroDoControleDeEstacionamento);
    }

    public boolean existeApartamentoBlocoEmUso(String apartamento, String bloco) {
        return controleDeEstacionamentoRepository.existsByApartamentoAndBloco(apartamento, bloco);
    }

}
