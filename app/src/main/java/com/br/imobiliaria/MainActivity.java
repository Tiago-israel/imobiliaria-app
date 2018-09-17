package com.br.imobiliaria;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.br.imobiliaria.constants.RequestCode;
import com.br.imobiliaria.utils.Carga;
import com.br.imobiliaria.utils.GerenciadorPreferencias;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Carga.criarCargaUsuariosGerente();
        Intent intent;
        if (GerenciadorPreferencias.usuarioLogado(this)) {
            intent = new Intent(this, ListagemImoveisActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivityForResult(intent, RequestCode.MAIN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.MAIN) {
            finish();
        }
    }


}
