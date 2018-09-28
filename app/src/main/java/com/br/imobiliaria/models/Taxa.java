package com.br.imobiliaria.models;

import com.orm.SugarRecord;

public class Taxa extends SugarRecord<Taxa> {

    private double taxaAteCemMil;
    private double taxaAteQuinhentosMil;
    private double taxaAcimaQuinhentosMil;

    public Taxa() {

    }

    public Taxa(double taxaAteCemMil, double taxaAteQuinhentosMil, double taxaAcimaQuinhentosMil) {
        this.taxaAteCemMil = taxaAteCemMil;
        this.taxaAteQuinhentosMil = taxaAteQuinhentosMil;
        this.taxaAcimaQuinhentosMil = taxaAcimaQuinhentosMil;
    }

    public double getTaxaAteCemMil() {
        return taxaAteCemMil;
    }

    public void setTaxaAteCemMil(double taxaAteCemMil) {
        this.taxaAteCemMil = taxaAteCemMil;
    }

    public double getTaxaAteQuinhentosMil() {
        return taxaAteQuinhentosMil;
    }

    public void setTaxaAteQuinhentosMil(double taxaAteQuinhentosMil) {
        this.taxaAteQuinhentosMil = taxaAteQuinhentosMil;
    }

    public double getTaxaAcimaQuinhentosMil() {
        return taxaAcimaQuinhentosMil;
    }

    public void setTaxaAcimaQuinhentosMil(double taxaAcimaQuinhentosMil) {
        this.taxaAcimaQuinhentosMil = taxaAcimaQuinhentosMil;
    }
}
