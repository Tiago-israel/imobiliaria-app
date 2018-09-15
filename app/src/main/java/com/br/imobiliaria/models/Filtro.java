package com.br.imobiliaria.models;

public class Filtro {
    public int quantidadeQuartos;
    public int preco;
    public String localidade;



    public Filtro(int quantidadeQuartos, int preco, String localidade) {
        this.quantidadeQuartos = quantidadeQuartos;
        this.preco = preco;
        this.localidade = localidade;
    }
}
