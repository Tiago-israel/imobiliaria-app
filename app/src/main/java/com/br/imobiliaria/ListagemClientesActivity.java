package com.br.imobiliaria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.br.imobiliaria.models.Cliente;
import com.br.imobiliaria.models.Imovel;
import com.br.imobiliaria.repositories.ClienteRepository;

import java.util.List;

public class ListagemClientesActivity extends AppCompatActivity {


    private ListView listViewClientes;
    private List<Cliente> clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_clientes);

        binding();
        preencherLista();

        listViewClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cliente cliente = clientes.get(i);
                cliente.setIdStr(String.valueOf(cliente.getId()));
                Intent intent = new Intent(getApplicationContext(), DetalhesClienteActivity.class);
                intent.putExtra("cliente", cliente);
                startActivity(intent);
            }
        });

    }

    public void binding() {
        this.listViewClientes = findViewById(R.id.listClientes);
    }

    public void novoCliente(View view) {
        startActivity(new Intent(this, CadastroClienteActivity.class));
    }

    public void preencherLista() {
        this.clientes = ClienteRepository.getInstance().findAll();
        ArrayAdapter<Cliente> lista = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, this.clientes);
        listViewClientes.setAdapter(lista);
    }


}
