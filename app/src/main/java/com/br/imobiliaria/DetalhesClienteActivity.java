package com.br.imobiliaria;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.br.imobiliaria.constants.RequestCode;
import com.br.imobiliaria.models.Cliente;
import com.br.imobiliaria.models.Financiamento;
import com.br.imobiliaria.models.Foto;
import com.br.imobiliaria.models.Imovel;
import com.br.imobiliaria.repositories.FinanciamentoRepository;
import com.br.imobiliaria.repositories.FotoRepository;
import com.br.imobiliaria.repositories.ImovelRepository;

import java.util.ArrayList;
import java.util.List;

public class DetalhesClienteActivity extends AppCompatActivity {

    private TextView nome, email, telefone;
    private ListView listViewImoveis;
    private List<Financiamento> financiamentos = new ArrayList<>();
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_cliente);
        cliente = (Cliente) getIntent().getExtras().get("cliente");
        this.binding();
        this.preencherLabelsCliente(cliente);
        this.preencherListFinanciamentos();

        listViewImoveis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Financiamento financiamento = financiamentos.get(i);
                Imovel imovel = financiamento.getImovel();
                imovel.setFotos(FotoRepository.getInstance().buscarFotosPorImovel(imovel.getId()));
                for (Foto foto:imovel.getFotos()){
                    foto.setIdAux(String.valueOf(foto.getId()));
                }
                imovel.setIdString(String.valueOf(imovel.getId()));
                Intent intent = new Intent(getApplicationContext(), DetalhesImovelActivity.class);
                intent.putExtra("imovel", imovel);
                intent.putExtra("parcelas",financiamento.getParcelas());
                intent.putExtra("detalheImovelCliente", true);
                startActivityForResult(intent, RequestCode.DETALHES_IMOVEL_CLIENTE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.DETALHES_IMOVEL_CLIENTE) {
            this.preencherListFinanciamentos();
        }
    }

    private void preencherLabelsCliente(Cliente cliente) {
        this.nome.setText("Nome: " + cliente.getNome());
        this.email.setText("Email: " + cliente.getEmail());
        this.telefone.setText("Telefone: " + cliente.getTelefone());
    }

    private void preencherListFinanciamentos() {
        financiamentos = FinanciamentoRepository.getInstance().buscarFinanciamentosPorCliente(Long.parseLong(cliente.getIdStr()));
        ArrayAdapter<Financiamento> financiamentoArrayAdapter = new ArrayAdapter<Financiamento>(this, android.R.layout.simple_list_item_1, financiamentos);
        listViewImoveis.setAdapter(financiamentoArrayAdapter);
    }


    private void binding() {
        nome = findViewById(R.id.detalheCliNome);
        email = findViewById(R.id.detalheCliEmail);
        telefone = findViewById(R.id.detalheCliTelefone);
        listViewImoveis = findViewById(R.id.detalheCliListImoveis);
    }
}
