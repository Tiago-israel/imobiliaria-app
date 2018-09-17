package com.br.imobiliaria.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.br.imobiliaria.constants.CodigosUsuario;
import com.br.imobiliaria.models.Usuario;

public class GerenciadorPreferencias {

    public static boolean usuarioLogado(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        String login = sharedPreferences.getString("login", "");
        String senha = sharedPreferences.getString("senha", "");
        return !login.equals("") && !senha.equals("");
    }

    public static  void salvarDadosUsuarioSharedPreference(Usuario usuario,Context context) {
        SharedPreferences preferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("login", usuario.getLogin());
        editor.putString("senha", usuario.getSenha());
        editor.putBoolean("isAdmin", usuario.getIsAdmin() == CodigosUsuario.GERENTE ? true : false);
        editor.commit();
    }

    public static boolean isAdmin(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean("isAdmin", false);
    }

    public static void limparUsuario(Context context){
        SharedPreferences preferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("login", null);
        editor.putString("senha", null);
        editor.commit();
    }
}
