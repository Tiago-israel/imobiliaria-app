package com.br.imobiliaria.utils;

import com.br.imobiliaria.models.Usuario;
import com.br.imobiliaria.repositories.UsuarioRepository;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class Carga {

    public static void criarCargaUsuariosGerente() {
        if(UsuarioRepository.getInstance().verificarExistenciaGerentesBase()){
            criarGerentes();
        }
    }

    private static void criarGerentes(){
        Usuario gerente1 = new Usuario("tiago","tiago77","123",1);
        Usuario gerente2 = new Usuario("marcelo","marcelo77","321",1);
        gerente1.save();
        gerente2.save();
    }

}
