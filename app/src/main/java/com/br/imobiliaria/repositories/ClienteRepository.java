package com.br.imobiliaria.repositories;

import com.br.imobiliaria.models.Cliente;
import com.br.imobiliaria.models.Usuario;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class ClienteRepository extends BaseRepository<Cliente> {

    static ClienteRepository clienteRepository;

    private ClienteRepository(Class<Cliente> clazz) {
        super(clazz);
    }

    public boolean verificarExistenciaEmail(String email) {
        List<Cliente> clientes = Select.from(Cliente.class)
                .where(
                        Condition.prop("email").eq(email)
                ).list();
        return !clientes.isEmpty();
    }


    public static ClienteRepository getInstance() {
        if (clienteRepository == null) {
            clienteRepository = new ClienteRepository(Cliente.class);
        }
        return clienteRepository;
    }
}
