package com.br.imobiliaria;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.br.imobiliaria.Interfaces.BaseActivity;
import com.br.imobiliaria.constants.RequestCode;
import com.br.imobiliaria.models.Usuario;
import com.br.imobiliaria.repositories.UsuarioRepository;
import com.br.imobiliaria.utils.GerenciadorPreferencias;

public class LoginActivity extends AppCompatActivity implements BaseActivity {

    private EditText login, senha;
    private TextView loginSenhaIncorreto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.binding();
        this.configurarTextos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.LOGIN) {
            finish();
        }
    }

    public void autenticar(View view) {
        if (this.validarCamposObrigatorios()) {
            Usuario usuario = UsuarioRepository.getInstance().login(extrairTextoEditText(login), extrairTextoEditText(senha));
            if (usuario != null) {
                this.configurarTextos();
                GerenciadorPreferencias.salvarDadosUsuarioSharedPreference(usuario, this);
                Intent intent = new Intent(this, ListagemImoveisActivity.class);
                intent.putExtra("UsuarioLogado", usuario);
                startActivityForResult(intent, RequestCode.LOGIN);
            } else {
                this.loginSenhaIncorreto.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean validarCamposObrigatorios() {
        boolean ehValido = true;
        if (this.verificarCampoVazio(login)) {
            this.escreverMensagemValidacao(login, "login");
            ehValido = false;
        }
        if (this.verificarCampoVazio(senha)) {
            this.escreverMensagemValidacao(senha, "senha");
            ehValido = false;
        }
        return ehValido;
    }

    @Override
    public void escreverMensagemValidacao(EditText campo, String nomeCampo) {
        campo.setError("O campo " + nomeCampo + " é obrigatório!");
    }

    @Override
    public String extrairTextoEditText(EditText campo) {
        return campo.getText().toString().trim();
    }

    @Override
    public boolean verificarCampoVazio(EditText campo) {
        return campo.getText().toString().trim().isEmpty();
    }

    @Override
    public void binding() {
        this.login = findViewById(R.id.loginUserLogin);
        this.senha = findViewById(R.id.loginUserSenha);
        this.loginSenhaIncorreto = findViewById(R.id.loginUserIncorreto);
    }

    private void configurarTextos() {
        this.loginSenhaIncorreto.setVisibility(View.GONE);
    }


}
