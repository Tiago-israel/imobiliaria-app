package com.br.imobiliaria.repositories;

import com.br.imobiliaria.models.Financiamento;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class FinanciamentoRepository extends BaseRepository<Financiamento> {

    private static FinanciamentoRepository financiamentoRepository;

    public FinanciamentoRepository(Class<Financiamento> clazz) {
        super(clazz);
    }


    public List<Financiamento> buscarFinanciamentosPorCliente(Long id) {
        List<Financiamento> financiamentos = Select.from(Financiamento.class)
                .where(
                        Condition.prop("cliente").eq(id)
                ).list();
        return financiamentos;
    }

    public void excluirFinanciamentosPorCliente(Long id) {
        List<Financiamento> financiamentos = Select.from(Financiamento.class)
                .where(
                        Condition.prop("imovel").eq(id)
                ).list();
        for (Financiamento financiamento : financiamentos) {
            this.delete(financiamento);
        }
    }

    public static FinanciamentoRepository getInstance() {
        if (financiamentoRepository == null) {
            financiamentoRepository = new FinanciamentoRepository(Financiamento.class);
        }
        return financiamentoRepository;
    }
}
