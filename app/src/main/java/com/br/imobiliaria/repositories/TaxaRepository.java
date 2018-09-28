package com.br.imobiliaria.repositories;

import com.br.imobiliaria.models.Taxa;

public class TaxaRepository extends BaseRepository {

    private static TaxaRepository taxaRepository;

    public TaxaRepository(Class clazz) {
        super(clazz);
    }

    public static TaxaRepository getInstance() {
        if (taxaRepository == null) {
            taxaRepository = new TaxaRepository(Taxa.class);
        }
        return taxaRepository;
    }

}
