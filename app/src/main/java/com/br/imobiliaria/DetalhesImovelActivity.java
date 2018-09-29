package com.br.imobiliaria;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.br.imobiliaria.constants.RequestCode;
import com.br.imobiliaria.models.Foto;
import com.br.imobiliaria.models.Imovel;
import com.br.imobiliaria.repositories.FinanciamentoRepository;
import com.br.imobiliaria.repositories.ImovelRepository;
import com.br.imobiliaria.utils.CalculoParcelas;
import com.br.imobiliaria.utils.CalculoValorImovel;
import com.br.imobiliaria.utils.TratamentoImagem;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

public class DetalhesImovelActivity extends AppCompatActivity {

    private SliderLayout slideImagens;
    private TextView nomeImovel, preco, localidade, descricao, quartos,parcelas;
    private Imovel imovel;
    private Button btnSimularReserva, btnCancelarReserva;
    private LinearLayout layoutParcelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_imovel);
        this.binding();
        this.imovel = (Imovel) getIntent().getExtras().get("imovel");
        boolean ehDetalhesImovelCliente = false;
        Integer parcelas = 3;
        try {
            ehDetalhesImovelCliente = getIntent().getExtras().getBoolean("detalheImovelCliente");
            parcelas = getIntent().getIntExtra("parcelas",3);
        } catch (Exception e) {

        }

        if (ehDetalhesImovelCliente) {
            this.btnSimularReserva.setVisibility(View.GONE);
            this.parcelas.setText(String.valueOf(parcelas)+"X de R$"+ CalculoValorImovel.formatarValor(CalculoParcelas.calcularParcelas(parcelas,imovel.getPreco())));
        } else {
            this.btnCancelarReserva.setVisibility(View.GONE);
            this.layoutParcelas.setVisibility(View.GONE);
        }

        this.carregarSlide(imovel.getFotos());
        this.carregarLabels(imovel);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.FINANCIAMENTO) {
            finish();
        }
    }

    public void financiarImovel(View view) {
        Intent intent = new Intent(this, SimulacaoFinanciamentoActivity.class);
        intent.putExtra("imovel", this.imovel);
        startActivityForResult(intent, RequestCode.FINANCIAMENTO);
    }

    public void cancelarFinanciamento(View view) {
        this.imovel.setFinanciado(0);
        this.imovel.setId(Long.parseLong(this.imovel.getIdString()));
        ImovelRepository.getInstance().save(this.imovel);
        FinanciamentoRepository.getInstance().excluirFinanciamentosPorCliente(Long.parseLong(this.imovel.getIdString()));
        Toast.makeText(this,"Imóvel cancelado com sucesso!",Toast.LENGTH_SHORT).show();
        finish();
    }

    private void carregarSlide(List<Foto> fotos) {
        for (Foto foto : fotos) {
            File filesDir = this.getFilesDir();
            File imageFile = new File(filesDir, foto.getIdAux() + ".jpg");
            OutputStream os;
            try {
                os = new FileOutputStream(imageFile);
                TratamentoImagem.convertArrayBytesToBitMapImage(foto.getArquivo())
                        .compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();
                TextSliderView textSliderView = new TextSliderView(this);
                textSliderView.description("").image(imageFile);
                slideImagens.addSlider(textSliderView);
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            }

        }

    }

    private void carregarLabels(Imovel imovel) {
        this.nomeImovel.setText(imovel.getNome());
        this.preco.setText(CalculoValorImovel.formatarValor(imovel.getPreco()));
        this.localidade.setText(imovel.getBairro());
        this.descricao.setText(imovel.getDescricao());
        this.quartos.setText(String.valueOf(imovel.getQuartos()));
    }


    private void binding() {
        this.slideImagens = findViewById(R.id.slider);
        this.nomeImovel = findViewById(R.id.detalhesNome);
        this.preco = findViewById(R.id.detalhesPreco);
        this.localidade = findViewById(R.id.detalhesLocal);
        this.descricao = findViewById(R.id.detalhesDescricao);
        this.quartos = findViewById(R.id.detalhesQuartos);
        this.btnCancelarReserva = findViewById(R.id.btnCancelarReserva);
        this.btnSimularReserva = findViewById(R.id.btnSimularReserva);
        this.layoutParcelas = findViewById(R.id.layoutParcelas);
        this.parcelas = findViewById(R.id.parcelas);
    }
}
