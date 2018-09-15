package com.br.imobiliaria.models;

public class Filtro {
    public int quantidadeQuartos;
    public Double preco;
    public String localidade;



    public Filtro(int quantidadeQuartos, Double preco, String localidade) {
        this.quantidadeQuartos = quantidadeQuartos;
        this.preco = preco;
        this.localidade = localidade;
    }
}
