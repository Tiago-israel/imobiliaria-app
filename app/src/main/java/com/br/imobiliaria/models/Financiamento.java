package com.br.imobiliaria.models;

import com.br.imobiliaria.utils.CalculoValorImovel;
import com.orm.SugarRecord;

import java.io.Serializable;

public class Financiamento extends SugarRecord<Financiamento> implements Serializable {

    private Cliente cliente;
    private Imovel imovel;
    private int parcelas;
    private double entrada;

    public Financiamento() {
    }

    public Financiamento(Cliente cliente, Imovel imovel, int parcelas,double entrada) {
        this.cliente = cliente;
        this.imovel = imovel;
        this.parcelas = parcelas;
        this.entrada = entrada;
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

    public double getEntrada() {
        return entrada;
    }

    public void setEntrada(double entrada) {
        this.entrada = entrada;
    }

    @Override
    public String toString() {
        return "Imóvel: " + imovel.getNome() + " | Valor: R$ " + CalculoValorImovel.formatarValor(imovel.getPreco());
    }
}
