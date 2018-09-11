package com.br.imobiliaria.repositories;

import com.br.imobiliaria.models.Imovel;

public class ImovelRepository extends BaseRepository<Imovel>  {

    private static  ImovelRepository imovelRepository;

    public ImovelRepository(Class<Imovel> clazz) {
        super(clazz);
    }

    public static ImovelRepository getInstance() {
        if (imovelRepository == null) {
            imovelRepository = new ImovelRepository(Imovel.class);
        }

        return imovelRepository;
    }
}
