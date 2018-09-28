package com.br.imobiliaria.repositories;

import com.br.imobiliaria.models.Taxa;

import java.util.List;

public class TaxaRepository extends BaseRepository {

    private static TaxaRepository taxaRepository;

    public TaxaRepository(Class clazz) {
        super(clazz);
    }

    public Taxa buscarTaxaCadastrada() {
        Taxa taxa = null;
        List<Taxa> taxas = this.findAll();
        if (!taxas.isEmpty()) {
            taxa = taxas.get(0);
        }
        return taxa;
    }

    public static TaxaRepository getInstance() {
        if (taxaRepository == null) {
            taxaRepository = new TaxaRepository(Taxa.class);
        }
        return taxaRepository;
    }

}
