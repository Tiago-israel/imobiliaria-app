package com.br.imobiliaria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.br.imobiliaria.Interfaces.BaseActivity;
import com.br.imobiliaria.models.Cliente;
import com.br.imobiliaria.models.Financiamento;
import com.br.imobiliaria.models.Imovel;
import com.br.imobiliaria.repositories.ClienteRepository;
import com.br.imobiliaria.repositories.FinanciamentoRepository;
import com.br.imobiliaria.repositories.ImovelRepository;

import java.util.List;

public class CadastroClienteActivity extends AppCompatActivity implements BaseActivity {

    private EditText nome, email, telefone;
    private Imovel imovel;
    private LinearLayout cadCliente, selectCliente;
    private Spinner spinnerCliente;
    private ImageView imgNovoCliente, imgHide;
    private List<Cliente> clientes;
    private double valorEntrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);
        this.binding();

        try {
            this.imovel = (Imovel) getIntent().getExtras().get("imovel");
            this.valorEntrada = getIntent().getDoubleExtra("valorEntrada",0);
        } catch (Exception e) {
        }

        this.cadCliente.setVisibility(View.GONE);
        this.imgHide.setVisibility(View.GONE);
        this.popularSpinnerPessoas();
    }

    @Override
    public void binding() {
        this.nome = findViewById(R.id.cadClienteNome);
        this.email = findViewById(R.id.cadClienteEmail);
        this.telefone = findViewById(R.id.cadClienteTelefone);
        this.cadCliente = findViewById(R.id.layoutCadCliente);
        this.selectCliente = findViewById(R.id.layouSelectCliente);
        this.spinnerCliente = findViewById(R.id.spinnerClientes);
        this.imgNovoCliente = findViewById(R.id.imgNovoCliente);
        this.imgHide = findViewById(R.id.imgHide);
    }

    public void novoCliente(View view) {
        this.selectCliente.setVisibility(View.GONE);
        this.cadCliente.setVisibility(View.VISIBLE);
        this.imgNovoCliente.setVisibility(View.GONE);
        this.imgHide.setVisibility(View.VISIBLE);
    }

    public void escolherClienteSelect(View view) {
        this.selectCliente.setVisibility(View.VISIBLE);
        this.cadCliente.setVisibility(View.GONE);
        this.imgNovoCliente.setVisibility(View.VISIBLE);
        this.imgHide.setVisibility(View.GONE);
    }

    private void popularSpinnerPessoas() {
        this.clientes = ClienteRepository.getInstance().findAll();
        if(this.clientes.size() > 0){
            ArrayAdapter<Cliente> clienteArrayAdapter = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, clientes);
            spinnerCliente.setAdapter(clienteArrayAdapter);
            selectCliente.setVisibility(View.VISIBLE);
        }else{
            selectCliente.setVisibility(View.GONE);
            imgHide.setVisibility(View.GONE);
            imgNovoCliente.setVisibility(View.GONE);
            cadCliente.setVisibility(View.VISIBLE);
        }

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

    public void gravarClienteCadastrado(View view) {
        Cliente cliente = (Cliente) spinnerCliente.getSelectedItem();
        ClienteRepository.getInstance().save(cliente);
        this.imovel.setFinanciado(1);
        this.imovel.setId(Long.parseLong(this.imovel.getIdString()));
        ImovelRepository.getInstance().save(this.imovel);
        Financiamento financiamento = new Financiamento(cliente, imovel, getIntent().getIntExtra("parcelas", 3),valorEntrada);
        FinanciamentoRepository.getInstance().save(financiamento);
        Toast.makeText(this, "Imóvel reservado com sucesso!", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void gravar(View view) {
        if (this.validarCamposObrigatorios()) {
            if (!ClienteRepository.getInstance().verificarExistenciaEmail(extrairTextoEditText(email))) {
                Cliente cliente = new Cliente(this.extrairTextoEditText(this.nome), this.extrairTextoEditText(this.email), this.extrairTextoEditText(this.telefone));
                ClienteRepository.getInstance().save(cliente);
                this.imovel.setFinanciado(1);
                this.imovel.setId(Long.parseLong(this.imovel.getIdString()));
                ImovelRepository.getInstance().save(this.imovel);
                Financiamento financiamento = new Financiamento(cliente, imovel, getIntent().getIntExtra("parcelas", 3),valorEntrada);
                FinanciamentoRepository.getInstance().save(financiamento);
                Toast.makeText(this, "Imóvel reservado com sucesso!", Toast.LENGTH_SHORT).show();
                this.finish();
            } else {
                email.setError("Email já cadastrado");
            }
        }
    }
}
