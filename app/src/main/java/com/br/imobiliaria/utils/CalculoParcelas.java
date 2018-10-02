package com.br.imobiliaria.utils;

public class CalculoParcelas {

    public static double calcularParcelas(int parcela, double valorTotal, double valorEntrada) {
        if (valorEntrada > 0) {
            valorTotal = valorTotal - valorEntrada;
        }
        return valorTotal / parcela;
    }


}
