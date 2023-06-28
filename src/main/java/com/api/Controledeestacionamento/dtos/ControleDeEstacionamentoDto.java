package com.api.Controledeestacionamento.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class ControleDeEstacionamentoDto {

    @NotBlank
    private String numeroDoControleDeEstacionamento;
    @NotBlank
    @Size(max = 7)
    private String placaDoCarro;
    @NotBlank
    private String marcaDoCarro;
    @NotBlank
    private String modeloDoCarro;
    @NotBlank
    private String corDoCarro;
    @NotBlank
    private  String nomeDoResponsavel;
    @NotBlank
    private String apartamento;
    @NotBlank
    private String bloco;

    public String getNumeroDoControleDeEstacionamento() {
        return numeroDoControleDeEstacionamento;
    }

    public void setNumeroDoControleDeEstacionamento(String numeroDoControleDeEstacionamento) {
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
