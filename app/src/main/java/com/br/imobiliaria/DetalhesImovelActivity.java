package com.br.imobiliaria;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.br.imobiliaria.models.Foto;
import com.br.imobiliaria.models.Imovel;
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
    private TextView nomeImovel, preco, localidade, descricao,quartos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_imovel);
        this.binding();
        Imovel imovel = (Imovel) getIntent().getExtras().get("imovel");

        this.carregarSlide(imovel.getFotos());
        this.carregarLabels(imovel);
    }


    private void carregarSlide(List<Foto> fotos) {
        for (Foto foto : fotos) {
            int cont =  Calendar.getInstance().get(Calendar.MILLISECOND);
            File filesDir = this.getFilesDir();
            File imageFile = new File(filesDir, cont + ".jpg");
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
        this.preco.setText(String.valueOf(imovel.getPreco()));
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
    }
}
