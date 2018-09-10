package com.br.imobiliaria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.br.imobiliaria.Interfaces.BaseActivity;
import com.br.imobiliaria.models.Usuario;
import com.br.imobiliaria.repositories.UsuarioRepository;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class CadastroUsuarioActivity extends AppCompatActivity implements BaseActivity {

    private EditText nome, login, senha, confirmSenha;
    private Switch admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        this.binding();
    }

    @Override
    public boolean validarCamposObrigatorios() {
        boolean ehValido = true;
        if (this.verificarCampoVazio(this.nome)) {
            this.escreverMensagemValidacao(this.nome, "nome");
            ehValido = false;
        }
        if (this.verificarCampoVazio(this.login)) {
            this.escreverMensagemValidacao(this.login, "login");
            ehValido = false;
        }
        if (this.verificarCampoVazio(this.senha)) {
            this.escreverMensagemValidacao(this.senha, "senha");
            ehValido = false;
        }
        if (this.verificarCampoVazio(this.confirmSenha)) {
            this.escreverMensagemValidacao(this.confirmSenha, "confirmar senha");
            ehValido = false;
        }
        return ehValido;
    }

    @Override
    public boolean verificarCampoVazio(EditText campo) {
        return campo.getText().toString().trim().isEmpty();
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
    public void binding() {
        this.nome = findViewById(R.id.cadUserNome);
        this.login = findViewById(R.id.cadUserLogin);
        this.senha = findViewById(R.id.cadUserSenha);
        this.confirmSenha = findViewById(R.id.cadUserConfirmSenha);
        this.admin = findViewById(R.id.cadUserAdmin);
    }

    public void salvarUsuario(View view) {
        if (this.validarCamposObrigatorios() && this.validarSenhas()) {
            if (validarExistenciaLogin()) {
                this.login.setError("Usuário "+extrairTextoEditText(login)+" já existe.");
            } else {
                Usuario usuario = new Usuario(extrairTextoEditText(nome), extrairTextoEditText(login), extrairTextoEditText(senha), admin.isChecked() ? 1 : 0);
                UsuarioRepository.getInstance().save(usuario);
                Toast.makeText(this, "Usuario cadastrado com sucesso", Toast.LENGTH_LONG).show();
                this.finish();
            }
        }
    }

    private boolean validarSenhas() {
        if (extrairTextoEditText(senha).equals(extrairTextoEditText(confirmSenha))) {
            return true;
        }
        this.confirmSenha.setError("As senhas não correspondem");
        return false;
    }

    private boolean validarExistenciaLogin() {
        return UsuarioRepository.getInstance().validarExistenciaLogin(extrairTextoEditText(login));
    }

}
