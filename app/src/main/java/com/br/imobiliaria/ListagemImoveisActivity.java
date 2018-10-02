package com.br.imobiliaria;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.provider.FontsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.br.imobiliaria.Interfaces.BaseActivity;
import com.br.imobiliaria.adapters.ListaImovelAdapter;
import com.br.imobiliaria.constants.RequestCode;
import com.br.imobiliaria.constants.ResultCode;
import com.br.imobiliaria.models.Filtro;
import com.br.imobiliaria.models.Foto;
import com.br.imobiliaria.models.Imovel;
import com.br.imobiliaria.repositories.FotoRepository;
import com.br.imobiliaria.repositories.ImovelRepository;
import com.br.imobiliaria.utils.GerenciadorPreferencias;

import java.util.ArrayList;
import java.util.List;

public class ListagemImoveisActivity extends AppCompatActivity implements BaseActivity {

    private LinearLayout layoutFiltro;
    private TextView quartos, valorEscolhido,mensagemPesquisaVazia;
    private SeekBar preco;
    private EditText filtroLocalidade;
    private ListView listViewImoveis;
    //menu itens
    private MenuItem novoImovel, novoVendedor, cadastrarTaxas, listarCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litagem_imoveis);
        this.binding();
        this.quartos.setText("1");
        this.configurarLayoutFiltro();
        this.preco.setMax(1000000);
        this.seekBarListener();
        this.valorEscolhido.setText("0");
        this.mensagemPesquisaVazia.setVisibility(View.GONE);
        this.configurarListView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.configurarListView();
    }

    public void Filtrar(View view) {
        Filtro filtro = new Filtro(Integer.parseInt(quartos.getText().toString()), Double.parseDouble(valorEscolhido.getText().toString()), extrairTextoEditText(filtroLocalidade));
        List<Imovel> imoveis = ImovelRepository.getInstance().filtrarImoveis(filtro);
        if(imoveis.size() == 0){
            this.mensagemPesquisaVazia.setVisibility(View.VISIBLE);
            this.listViewImoveis.setVisibility(View.GONE);
        }else{
            for (Imovel imovel : imoveis) {
                imovel.setFotos(FotoRepository.getInstance().buscarFotosPorImovel(imovel.getId()));
            }
            ListaImovelAdapter listaImovelAdapter = new ListaImovelAdapter(this, imoveis);
            this.listViewImoveis.setAdapter(listaImovelAdapter);
            this.listViewImoveis.setVisibility(View.VISIBLE);
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        this.novoImovel = menu.findItem(R.id.menuNovoImovel);
        this.novoVendedor = menu.findItem(R.id.menuNovoVendedor);
        this.cadastrarTaxas = menu.findItem(R.id.menuCadastrarTaxa);
        this.listarCliente = menu.findItem(R.id.menuListarCliente);
        this.configurarMenuUsuario();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuNovoImovel:
                this.navegarParaActivity(CadastroImovelActivity.class, RequestCode.CAD_IMOVEL);
                break;
            case R.id.menuNovoVendedor:
                this.navegarParaActivity(CadastroUsuarioActivity.class, RequestCode.CAD_VENDEDOR);
                break;
            case R.id.menuListarCliente:
                this.navegarParaActivity(ListagemClientesActivity.class, RequestCode.LST_CLIENTE);
                break;
            case R.id.menuCadastrarTaxa:
                this.navegarParaActivity(CadastroTaxaActivity.class, RequestCode.CAD_TAXAS);
                break;
            case R.id.menuSair:
                GerenciadorPreferencias.limparUsuario(this);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case ResultCode.IMOVEL_CADASTRADO_SUCESSO:
                this.configurarListView();
                break;
            default:
                break;
        }
    }

    @Override
    public void binding() {
        this.layoutFiltro = findViewById(R.id.layoutFiltro);
        this.quartos = findViewById(R.id.textQuartos);
        this.valorEscolhido = findViewById(R.id.valorEscolhido);
        this.preco = findViewById(R.id.seekBarPreco);
        this.filtroLocalidade = findViewById(R.id.filtroLocalidade);
        this.listViewImoveis = findViewById(R.id.listImoveis);
        this.mensagemPesquisaVazia = findViewById(R.id.mensagemPesquisaVazia);
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
        List<Imovel>  imoveis = tratarListaImoveis(ImovelRepository.getInstance(). buscarImoveisDisponiveisParaFinanciamento());
        if (imoveis.size() == 0) {
            this.mensagemPesquisaVazia.setText("Nenhum imóvel cadastrado.");
            this.mensagemPesquisaVazia.setVisibility(View.VISIBLE);
            this.listViewImoveis.setVisibility(View.GONE);
        }else{
            for (Imovel imovel : imoveis) {
                imovel.setFotos(FotoRepository.getInstance().buscarFotosPorImovel(imovel.getId()));
            }
            ListaImovelAdapter listaImovelAdapter = new ListaImovelAdapter(this, imoveis);
            this.listViewImoveis.setAdapter(listaImovelAdapter);
            this.mensagemPesquisaVazia.setVisibility(View.GONE);
            this.listViewImoveis.setVisibility(View.VISIBLE);
        }
    }

    private void navegarParaActivity(Class clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    private void configurarMenuUsuario() {
        if (!GerenciadorPreferencias.isAdmin(this)) {
            this.novoImovel.setVisible(false);
            this.novoVendedor.setVisible(false);
            cadastrarTaxas.setVisible(false);
        }
    }

    private List<Imovel> tratarListaImoveis(List<Imovel>imoveis){
        for (Imovel imovel : imoveis){
            imovel.setIdString(String.valueOf(imovel.getId()));
        }
        return imoveis;
    }
}
