package com.br.imobiliaria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.br.imobiliaria.Interfaces.BaseActivity;
import com.br.imobiliaria.models.Foto;
import com.br.imobiliaria.models.Imovel;
import com.br.imobiliaria.repositories.ImovelRepository;

import java.util.List;


public class CadastroImovelActivity extends AppCompatActivity implements BaseActivity {

    private EditText nome, preco, bairro, descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_imovel);
        this.binding();
    }

    @Override
    public void escreverMensagemValidacao(EditText campo, String nomeCampo) {
        campo.setError("O campo " + nomeCampo + " é obrigatório!");
    }

    @Override
    public boolean verificarCampoVazio(EditText campo) {
        return campo.getText().toString().trim().isEmpty();
    }

    @Override
    public boolean validarCamposObrigatorios() {
        boolean ehValido = true;
        if (this.verificarCampoVazio(this.nome)) {
            this.escreverMensagemValidacao(this.nome, "nome");
            ehValido = false;
        }
        if (this.verificarCampoVazio(this.preco)) {
            this.escreverMensagemValidacao(this.preco, "preco");
            ehValido = false;
        }
        if (this.verificarCampoVazio(this.bairro)) {
            this.escreverMensagemValidacao(this.bairro, "bairro");
            ehValido = false;
        }
        return ehValido;
    }

    @Override
    public String extrairTextoEditText(EditText campo) {
        return campo.getText().toString().trim();
    }

    @Override
    public void binding() {
        this.nome = findViewById(R.id.cadNome);
        this.preco = findViewById(R.id.cadPreco);
        this.bairro = findViewById(R.id.cadBairro);
        this.descricao = findViewById(R.id.cadDescricao);
    }

    public void salvarImovel(View view) {
        if (this.validarCamposObrigatorios()) {

                Imovel imovel = new Imovel(extrairTextoEditText(nome), Double.parseDouble(extrairTextoEditText(preco)), extrairTextoEditText(bairro), extrairTextoEditText(descricao));
                ImovelRepository.getInstance().save(imovel);
                Toast.makeText(this, "Imovel cadastrado com sucesso ", Toast.LENGTH_LONG).show();
                List<Imovel> listaImovel = Imovel.listAll(Imovel.class);
                for(Imovel imovelx : listaImovel){
                    Toast.makeText(this, "-" + imovelx.getNome(), Toast.LENGTH_LONG).show();
                }
                //this.finish();

        }
    }
}
