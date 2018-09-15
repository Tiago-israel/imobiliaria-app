package com.br.imobiliaria.repositories;

import com.br.imobiliaria.models.Usuario;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class UsuarioRepository extends BaseRepository<Usuario> {

    private static UsuarioRepository usuarioRepository;

    private UsuarioRepository(Class<Usuario> clazz) {
        super(clazz);
    }

    public Usuario login(String login, String senha) {
        final Usuario usuario;
        List<Usuario> usuarios = Select.from(Usuario.class)
                .where(
                        Condition.prop("login").eq(login),
                        Condition.prop("senha").eq(senha)
                ).list();
        if(usuarios.isEmpty()){
            return null;
        }
        return usuarios.get(0);
    }

    public boolean validarExistenciaLogin(String login) {
        List<Usuario> usuarios = Select.from(Usuario.class)
                .where(
                        Condition.prop("login").eq(login)
                ).list();
        return !usuarios.isEmpty();
    }

    public boolean verificarExistenciaGerentesBase(){
        List<Usuario> usuarios = Select.from(Usuario.class)
                .where(
                        Condition.prop("is_admin").eq(1)
                ).list();
        return usuarios.isEmpty();
    }

    public static UsuarioRepository getInstance() {
        if (usuarioRepository == null) {
            usuarioRepository = new UsuarioRepository(Usuario.class);
        }
        return usuarioRepository;
    }
}
