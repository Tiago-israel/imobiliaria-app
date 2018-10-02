package com.br.imobiliaria.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.br.imobiliaria.models.Filtro;
import com.br.imobiliaria.models.Imovel;
import com.orm.SugarDb;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class ImovelRepository extends BaseRepository<Imovel> {

    private static ImovelRepository imovelRepository;

    public ImovelRepository(Class<Imovel> clazz) {
        super(clazz);
    }

    public List<Imovel> filtrarImoveis(Filtro filtro) {
        List<Imovel> imoveis = Select.from(Imovel.class)
                .where(
                        Condition.prop("bairro").like("%" + filtro.localidade + "%"),
                        Condition.prop("quartos").eq(filtro.quantidadeQuartos),
                        Condition.prop("preco").lt(filtro.preco + 1),
                        Condition.prop("financiado").eq(0)
                ).list();
        return imoveis;
    }

    public List<Imovel> buscarImoveisDisponiveisParaFinanciamento() {
        List<Imovel> imoveis = Select.from(Imovel.class)
                .where(
                        Condition.prop("financiado").eq(0)
                ).list();
        return imoveis;
    }

    public double obterMaiorValorImovel(Context context){
        SugarDb sugarDb = new SugarDb(context);
        double maiorValor = 0;
        SQLiteDatabase database = sugarDb.getReadableDatabase();
        SQLiteStatement query = database.compileStatement("SELECT MAX(preco) FROM imovel");
        try {
            maiorValor = query.simpleQueryForLong();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            query.close();
        }
        return maiorValor;
    }

    public static ImovelRepository getInstance() {
        if (imovelRepository == null) {
            imovelRepository = new ImovelRepository(Imovel.class);
        }
        return imovelRepository;
    }
}
