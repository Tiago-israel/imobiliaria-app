package com.br.imobiliaria.models;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Financiamento extends SugarRecord<Financiamento> implements Serializable {

    private Cliente cliente;
    private Imovel imovel;
    private int parcelas;

    public Financiamento() {
    }

    public Financiamento(Cliente cliente, Imovel imovel, int parcelas) {
        this.cliente = cliente;
        this.imovel = imovel;
        this.parcelas = parcelas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    @Override
    public String toString() {
        return "Imóvel: " + imovel.getNome() + " | Valor: R$ " + imovel.getPreco();
    }
}
