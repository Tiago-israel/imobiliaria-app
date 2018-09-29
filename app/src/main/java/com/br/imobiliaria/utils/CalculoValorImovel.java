package com.br.imobiliaria.utils;

import com.br.imobiliaria.models.Taxa;
import com.br.imobiliaria.repositories.TaxaRepository;

import java.text.DecimalFormat;

public class CalculoValorImovel {

    public static double calcular(double valor) {
        Taxa taxa = TaxaRepository.getInstance().buscarTaxaCadastrada();
        if (taxa != null) {
            double valorCalculado = valor;
            if (valor <= 100000) {
                valorCalculado += valorCalculado * (taxa.getTaxaAteCemMil() / 100);
            } else if (valor > 100000 && valor <= 500000) {
                valorCalculado += valorCalculado * (taxa.getTaxaAteQuinhentosMil() / 100);
            } else {
                valorCalculado += valorCalculado * (taxa.getTaxaAcimaQuinhentosMil() / 100);
            }
            return valorCalculado;
        }
        return valor;
    }

    public static String formatarValor(Double valor) {
        if (valor != null) {
            return new DecimalFormat("####0.00").format(valor);
        }
        return "0.0";
    }

}
