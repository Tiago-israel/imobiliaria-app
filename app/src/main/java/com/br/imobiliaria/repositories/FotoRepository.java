package com.br.imobiliaria.repositories;

import com.br.imobiliaria.models.Foto;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class FotoRepository extends BaseRepository<Foto> {

    private static FotoRepository fotoRepository;

    private FotoRepository(Class<Foto> clazz) {
        super(clazz);
    }

    public List<Foto>buscarFotosPorImovel(Long id){
        List<Foto> fotos = Select.from(Foto.class)
                .where(
                        Condition.prop("imovel").eq(id)
                ).list();
        return fotos;
    }

    public static FotoRepository getInstance() {
        if (fotoRepository == null) {
            fotoRepository = new FotoRepository(Foto.class);
        }
        return fotoRepository;
    }
}
