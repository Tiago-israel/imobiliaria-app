package com.br.imobiliaria;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.br.imobiliaria.constants.RequestCode;
import com.br.imobiliaria.models.Imovel;
import com.br.imobiliaria.utils.CalculoParcelas;
import com.br.imobiliaria.utils.CalculoValorImovel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SimulacaoFinanciamentoActivity extends AppCompatActivity {

    Spinner parcelas;
    TextView valor, qtdParcelas, valorParcelado;
    private Imovel imovel;
    private EditText valorEntrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulacao_financiamento);
        this.binding();
        imovel = (Imovel) getIntent().getExtras().get("imovel");
        this.criarParcelas();
        this.configurarSpinner();
        this.carregarLabels(imovel);

    }

    public void reservar(View view) {
        double entrada = 0.0;
        if (!valorEntrada.getText().toString().isEmpty()){
            entrada = Double.parseDouble(valorEntrada.getText().toString().replace(".", "").replace(",", "."));
        }
        Intent intent = new Intent(this, CadastroClienteActivity.class);
        intent.putExtra("parcelas", Integer.parseInt(qtdParcelas.getText().toString().replace("X", "")));
        intent.putExtra("valorEntrada", entrada);
        intent.putExtra("imovel", this.imovel);
        startActivityForResult(intent, RequestCode.CAD_CLIENTE);
    }

    public void calcularValorParcerla(View view) {
        int parcela = (Integer) this.parcelas.getSelectedItem();
        this.qtdParcelas.setText(String.valueOf(parcela) + "X");
        double entrada = 0.0;
        if (!valorEntrada.getText().toString().isEmpty()){
            entrada = Double.parseDouble(valorEntrada.getText().toString().replace(".", "").replace(",", "."));
        }
        this.valorParcelado.setText("R$ " + CalculoValorImovel.formatarValor(CalculoParcelas.calcularParcelas(parcela, imovel.getPreco(), entrada)));
    }

    private void carregarLabels(Imovel imovel) {
        this.valor.setText("R$ " + CalculoValorImovel.formatarValor(imovel.getPreco()));
        this.qtdParcelas.setText(String.valueOf(3) + "X");
        this.valorParcelado.setText("R$ " + CalculoValorImovel.formatarValor(CalculoParcelas.calcularParcelas(3, imovel.getPreco(), 0)));
    }

    private void binding() {
        this.valor = findViewById(R.id.financiamentoValor);
        this.qtdParcelas = findViewById(R.id.financiamentoQtdPaecela);
        this.valorParcelado = findViewById(R.id.financiamentoValorParcelado);
        this.parcelas = findViewById(R.id.financiamentoParcelas);
        this.valorEntrada = findViewById(R.id.valorEntrada);
    }

    private void configurarSpinner() {
        ArrayAdapter<Integer> numParcelas = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, criarParcelas());
        parcelas.setAdapter(numParcelas);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.CAD_CLIENTE) {
            finish();
        }
    }

    private List<Integer> criarParcelas() {
        List<Integer> parcelas = new ArrayList<>();
        parcelas.add(3);
        parcelas.add(6);
        parcelas.add(12);
        parcelas.add(24);
        parcelas.add(36);
        parcelas.add(72);
        return parcelas;
    }

}
