package com.api.Controledeestacionamento.repositories;

import com.api.Controledeestacionamento.models.ControleDeEstacionamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ControleDeEstacionamentoRepository extends JpaRepository<ControleDeEstacionamentoModel, UUID> {

    boolean existsByPlacaDoCarro(String placaDoCarro);
    boolean existsByVagaDoEstacionamento(Integer vagaDoEstacionamento);
    boolean existsByApartamentoAndBloco(String apartamento, String bloco);

}
