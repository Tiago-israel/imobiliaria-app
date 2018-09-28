package com.br.imobiliaria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.br.imobiliaria.models.Cliente;
import com.br.imobiliaria.repositories.ClienteRepository;

public class ListagemClientesActivity extends AppCompatActivity {


    private ListView listViewClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_clientes);

        binding();
        preencherLista();
    }

    public void binding() {
        this.listViewClientes = findViewById(R.id.listClientes);
    }
    public void novoCliente(View view){
        startActivity(new Intent(this, CadastroClienteActivity.class));
    }
    public void preencherLista(){
        ArrayAdapter<Cliente> lista = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, ClienteRepository.getInstance().findAll());
        listViewClientes.setAdapter(lista);
    }
}
