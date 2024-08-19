package com.api.Controledeestacionamento.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_PARKING_SPOT")
public class ControleDeEstacionamentoModel implements Serializable {
    private static final long serialVerionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true, length = 10)
    private Integer numeroDoControleDeEstacionamento;
    @Column(nullable = false, unique = true, length = 7)
    private String placaDoCarro;
    @Column(nullable = false, length = 70)
    private String marcaDoCarro;
    @Column(nullable = false, length = 70)
    private String modeloDoCarro;
    @Column(nullable = false, length = 70)
    private String corDoCarro;
    @Column(nullable = false)
    private LocalDateTime dataDeRegistro;
    @Column(nullable = false, length = 130)
    private String nomeDoResponsavel;
    @Column(nullable = false, length = 30)
    private String apartamento;
    @Column(nullable = false, length = 30)
    private String bloco;

    public ControleDeEstacionamentoModel(Integer numeroDoControleDeEstacionamento, String placaDoCarro, String marcaDoCarro, String modeloDoCarro, String corDoCarro, LocalDateTime dataDeRegistro, String nomeDoResponsavel, String apartamento, String bloco) {
        this.numeroDoControleDeEstacionamento = numeroDoControleDeEstacionamento;
        this.placaDoCarro = placaDoCarro;
        this.marcaDoCarro = marcaDoCarro;
        this.modeloDoCarro = modeloDoCarro;
        this.corDoCarro = corDoCarro;
        this.dataDeRegistro = dataDeRegistro;
        this.nomeDoResponsavel = nomeDoResponsavel;
        this.apartamento = apartamento;
        this.bloco = bloco;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getNumeroDoControleDeEstacionamento() {
        return numeroDoControleDeEstacionamento;
    }

    public void setNumeroDoControleDeEstacionamento(Integer numeroDoControleDeEstacionamento) {
        this.numeroDoControleDeEstacionamento = numeroDoControleDeEstacionamento;
    }

    public String getPlacaDoCarro() {
        return placaDoCarro;
    }

    public void setPlacaDoCarro(String placaDoCarro) {
        this.placaDoCarro = placaDoCarro;
    }

    public String getMarcaDoCarro() {
        return marcaDoCarro;
    }

    public void setMarcaDoCarro(String marcaDoCarro) {
        this.marcaDoCarro = marcaDoCarro;
    }

    public String getModeloDoCarro() {
        return modeloDoCarro;
    }

    public void setModeloDoCarro(String modeloDoCarro) {
        this.modeloDoCarro = modeloDoCarro;
    }

    public String getCorDoCarro() {
        return corDoCarro;
    }

    public void setCorDoCarro(String corDoCarro) {
        this.corDoCarro = corDoCarro;
    }

    public LocalDateTime getDataDeRegistro() {
        return dataDeRegistro;
    }

    public void setDataDeRegistro(LocalDateTime dataDeRegistro) {
        this.dataDeRegistro = dataDeRegistro;
    }

    public String getNomeDoResponsavel() {
        return nomeDoResponsavel;
    }

    public void setNomeDoResponsavel(String nomeDoResponsavel) {
        this.nomeDoResponsavel = nomeDoResponsavel;
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    public String getBloco() {
        return bloco;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }
}
