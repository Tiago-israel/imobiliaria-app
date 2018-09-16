package com.br.imobiliaria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.br.imobiliaria.models.Usuario;
import com.br.imobiliaria.repositories.UsuarioRepository;
import com.br.imobiliaria.utils.Carga;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Carga.criarCargaUsuariosGerente();
//        List<Usuario>users = UsuarioRepository.getInstance().findAll();
//        for (Usuario usuario :users ){
//            Toast.makeText(getApplicationContext(),usuario.getNome(),Toast.LENGTH_SHORT).show();
//        }
        //startActivity(new Intent(this, CadastroImovelActivity.class));
        startActivity(new Intent(this, LitagemImoveisActivity.class));
    }


}
