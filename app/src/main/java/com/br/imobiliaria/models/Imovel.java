package com.br.imobiliaria.models;

import java.util.List;

import com.br.imobiliaria.utils.CalculoValorImovel;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;

public class Imovel extends SugarRecord<Imovel> implements Serializable {

    @Ignore
    private String idString;
    private String nome;
    private Double preco;
    private String bairro;
    private Integer quartos;
    private String descricao;
    private int financiado = 0;

    @Ignore
    private List<Foto> fotos;

    public Imovel() {
    }

    public Imovel(String nome, Double preco, String bairro, Integer quartos, String descricao) {
        this.nome = nome;
        this.preco = preco;
        this.bairro = bairro;
        this.quartos = quartos;
        this.descricao = descricao;
    }

    public Imovel(String nome, Double preco, String bairro, Integer quartos, String descricao, List<Foto> fotos) {
        this.nome = nome;
        this.preco = preco;
        this.bairro = bairro;
        this.quartos = quartos;
        this.descricao = descricao;
        this.fotos = fotos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = CalculoValorImovel.calcular(preco);
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    public Integer getQuartos() {
        return quartos;
    }

    public void setQuartos(Integer quartos) {
        this.quartos = quartos;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public Foto obterFotoPrincipal() {
        Foto fotoPrincipal = new Foto();
        for (Foto foto : this.fotos) {
            if (foto.getIsMain() == 1) {
                fotoPrincipal = foto;
            }
        }
        return fotoPrincipal;
    }

    public int getFinanciado() {
        return financiado;
    }

    public void setFinanciado(int financiado) {
        this.financiado = financiado;
    }
}
