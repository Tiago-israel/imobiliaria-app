package com.br.imobiliaria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.br.imobiliaria.Interfaces.BaseActivity;
import com.br.imobiliaria.adapters.ListaImovelAdapter;
import com.br.imobiliaria.models.Filtro;
import com.br.imobiliaria.models.Foto;
import com.br.imobiliaria.models.Imovel;
import com.br.imobiliaria.repositories.FotoRepository;
import com.br.imobiliaria.repositories.ImovelRepository;

import java.util.List;

public class LitagemImoveisActivity extends AppCompatActivity implements BaseActivity {

    private LinearLayout layoutFiltro;
    private TextView quartos, valorEscolhido;
    private SeekBar preco;
    private EditText filtroLocalidade;
    private ListView listViewImoveis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litagem_imoveis);
        this.binding();
        this.quartos.setText("1");
        this.configurarLayoutFiltro();
        this.preco.setMax(200000);
        this.seekBarListener();
        this.configurarListView();
    }

    public void Filtrar(View view){
        Filtro filtro = new Filtro(Integer.parseInt(quartos.getText().toString()),Double.parseDouble(valorEscolhido.getText().toString()),extrairTextoEditText(filtroLocalidade));
    }

    public void mostrarEsconderFiltro(View view) {
        if (layoutFiltro.getVisibility() == View.VISIBLE) {
            layoutFiltro.setVisibility(View.GONE);
        } else {
            layoutFiltro.setVisibility(View.VISIBLE);
        }
    }

    public void subitrairQuartos(View view) {
        int numeroQaurto = Integer.parseInt(this.quartos.getText().toString());
        if (numeroQaurto > 1) {
            numeroQaurto--;
        }
        this.quartos.setText(String.valueOf(numeroQaurto));
    }

    public void adicionarQuartos(View view) {
        int numeroQaurto = Integer.parseInt(this.quartos.getText().toString());
        numeroQaurto++;
        this.quartos.setText(String.valueOf(numeroQaurto));
    }

    private void configurarLayoutFiltro() {
        layoutFiltro.setVisibility(View.GONE);
    }

    private void seekBarListener() {
        preco.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressChangedValue = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                valorEscolhido.setText(String.valueOf(progressChangedValue));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                valorEscolhido.setText(String.valueOf(progressChangedValue));
            }
        });
    }

    @Override
    public void binding() {
        this.layoutFiltro = findViewById(R.id.layoutFiltro);
        this.quartos = findViewById(R.id.textQuartos);
        this.valorEscolhido = findViewById(R.id.valorEscolhido);
        this.preco = findViewById(R.id.seekBarPreco);
        this.filtroLocalidade = findViewById(R.id.filtroLocalidade);
        this.listViewImoveis = findViewById(R.id.listImoveis);
    }

    @Override
    public void escreverMensagemValidacao(EditText campo, String nomeCampo) {

    }

    @Override
    public boolean verificarCampoVazio(EditText campo) {
        return false;
    }

    @Override
    public boolean validarCamposObrigatorios() {
        return false;
    }

    @Override
    public String extrairTextoEditText(EditText campo) {
        return campo.getText().toString().trim();
    }

    private void configurarListView() {
        List<Imovel>imoveis = ImovelRepository.getInstance().findAll();
        for (Imovel imovel:imoveis){
            imovel.setFotos(FotoRepository.getInstance().buscarFotosPorImovel(imovel.getId()));
        }
        ListaImovelAdapter  listaImovelAdapter = new ListaImovelAdapter(this, imoveis);
        this.listViewImoveis.setAdapter(listaImovelAdapter);
    }
}
