package com.br.imobiliaria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.br.imobiliaria.Interfaces.BaseActivity;
import com.br.imobiliaria.models.Cliente;
import com.br.imobiliaria.models.Financiamento;
import com.br.imobiliaria.models.Imovel;
import com.br.imobiliaria.repositories.ClienteRepository;
import com.br.imobiliaria.repositories.FinanciamentoRepository;
import com.br.imobiliaria.repositories.ImovelRepository;

public class CadastroClienteActivity extends AppCompatActivity implements BaseActivity {

    private EditText nome, email, telefone;
    private Imovel imovel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);
        this.binding();

        try {
            this.imovel = (Imovel) getIntent().getExtras().get("imovel");
        } catch (Exception e) {
        }
    }

    @Override
    public void binding() {
        this.nome = findViewById(R.id.cadClienteNome);
        this.email = findViewById(R.id.cadClienteEmail);
        this.telefone = findViewById(R.id.cadClienteTelefone);
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
        if (this.verificarCampoVazio(this.email)) {
            this.escreverMensagemValidacao(this.email, "email");
            ehValido = false;
        }
        if (this.verificarCampoVazio(this.telefone)) {
            this.escreverMensagemValidacao(this.telefone, "telefone");
            ehValido = false;
        }
        return ehValido;
    }

    @Override
    public String extrairTextoEditText(EditText campo) {
        return campo.getText().toString().trim();
    }

    public void gravar(View view) {
        if (this.validarCamposObrigatorios()) {
            if (!ClienteRepository.getInstance().verificarExistenciaEmail(extrairTextoEditText(email))) {
                Cliente cliente = new Cliente(this.extrairTextoEditText(this.nome), this.extrairTextoEditText(this.email), this.extrairTextoEditText(this.telefone));
                ClienteRepository.getInstance().save(cliente);
                this.imovel.setFinanciado(1);
                this.imovel.setId(Long.parseLong(this.imovel.getIdString()));
                ImovelRepository.getInstance().save(this.imovel);
                Financiamento financiamento = new Financiamento(cliente, imovel, getIntent().getIntExtra("parcelas", 3));
                FinanciamentoRepository.getInstance().save(financiamento);
                Toast.makeText(this, "Imóvel reservado com sucesso!", Toast.LENGTH_SHORT).show();
                this.finish();
            }else{
                email.setError("Email já cadastrado");
            }
        }
    }
}
