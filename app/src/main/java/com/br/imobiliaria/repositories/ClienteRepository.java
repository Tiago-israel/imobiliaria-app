package com.br.imobiliaria.repositories;

import com.br.imobiliaria.models.Cliente;
import com.br.imobiliaria.models.Usuario;

public class ClienteRepository extends BaseRepository<Cliente> {

    static ClienteRepository clienteRepository;

    private ClienteRepository(Class<Cliente> clazz) {
        super(clazz);
    }

    public static ClienteRepository getInstance() {
        if (clienteRepository == null) {
            clienteRepository = new ClienteRepository(Cliente.class);
        }
        return clienteRepository;
    }
}
