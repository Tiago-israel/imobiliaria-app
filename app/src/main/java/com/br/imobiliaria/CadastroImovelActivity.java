package com.br.imobiliaria;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.br.imobiliaria.Interfaces.BaseActivity;
import com.br.imobiliaria.constants.ResultCode;
import com.br.imobiliaria.models.Foto;
import com.br.imobiliaria.models.Imovel;
import com.br.imobiliaria.repositories.FotoRepository;
import com.br.imobiliaria.repositories.ImovelRepository;
import com.br.imobiliaria.utils.TratamentoImagem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class CadastroImovelActivity extends AppCompatActivity implements BaseActivity {

    private EditText nome, preco, bairro, descricao, quartos;
    private ImageView foto1, foto2, foto3, foto4;
    private Foto[] fotos = new Foto[4];
    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private boolean isFoto1 = false, isFoto2 = false, isFoto3 = false, isFoto4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_imovel);
        this.binding();
    }

    //Tratamento das imagens

    public void tirarFoto1(View view) {
        this.setarEstadoFotoAtual(true, false, false, false);
    }

    public void tirarFoto2(View view) {
        this.setarEstadoFotoAtual(false, true, false, false);
    }

    public void tirarFoto3(View view) {
        this.setarEstadoFotoAtual(false, false, true, false);
    }

    public void tirarFoto4(View view) {
        this.setarEstadoFotoAtual(false, false, false, true);
    }

    private void setarEstadoFotoAtual(boolean isFoto1, boolean isFoto2, boolean isFoto3, boolean isFoto4) {
        this.isFoto1 = isFoto1;
        this.isFoto2 = isFoto2;
        this.isFoto3 = isFoto3;
        this.isFoto4 = isFoto4;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private void tratarFluxoImagens(Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            Bitmap imagem = (Bitmap) bundle.get("data");
            Foto foto = new Foto();
            foto.setArquivo(TratamentoImagem.converterBitMapToBase64(imagem));
            if (isFoto1) {
                foto.setIsMain(1);
                this.adicionarImagemListaFotos(0, foto);
                carregarImagemTela(foto1, imagem);
            } else if (isFoto2) {
                this.adicionarImagemListaFotos(1, foto);
                carregarImagemTela(foto2, imagem);
            } else if (isFoto3) {
                this.adicionarImagemListaFotos(2, foto);
                carregarImagemTela(foto3, imagem);
            } else if (isFoto4) {
                this.adicionarImagemListaFotos(3, foto);
                carregarImagemTela(foto4, imagem);
            }
        }
    }

    private void adicionarImagemListaFotos(int index, Foto foto) {
        try {
            this.fotos[index] = foto;
        } catch (Exception ex) {

        }
    }

    private void carregarImagemTela(ImageView imageView, Bitmap imagem) {
        imageView.setImageBitmap(imagem);
    }

    //fim tratamento imagens

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            this.tratarFluxoImagens(data);
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
        if (this.verificarCampoVazio(this.preco)) {
            this.escreverMensagemValidacao(this.preco, "preco");
            ehValido = false;
        }
        if (this.verificarCampoVazio(this.bairro)) {
            this.escreverMensagemValidacao(this.bairro, "bairro");
            ehValido = false;
        }
        if (this.verificarCampoVazio(this.quartos)) {
            this.escreverMensagemValidacao(this.quartos, "Quantidade de quartos");
            ehValido = false;
        }
        return ehValido;
    }

    @Override
    public String extrairTextoEditText(EditText campo) {
        return campo.getText().toString().trim();
    }

    @Override
    public void binding() {
        this.nome = findViewById(R.id.cadNome);
        this.preco = findViewById(R.id.cadPreco);
        this.bairro = findViewById(R.id.cadBairro);
        this.descricao = findViewById(R.id.cadDescricao);
        this.quartos = findViewById(R.id.qtdQuartos);
        this.foto1 = findViewById(R.id.foto1);
        this.foto2 = findViewById(R.id.foto2);
        this.foto3 = findViewById(R.id.foto3);
        this.foto4 = findViewById(R.id.foto4);
    }

    private Double tratarValorComMascara(String valor) {
        String valorStr = valor.replace(".", "").replace(",", ".");
        return Double.parseDouble(valorStr);
    }

    public void salvarImovel(View view) {
        try {
            if (this.validarCamposObrigatorios()) {
                if (this.fotos.length == 4) {
                    Imovel imovel = new Imovel(extrairTextoEditText(nome), tratarValorComMascara(extrairTextoEditText(preco)), extrairTextoEditText(bairro), Integer.parseInt(extrairTextoEditText(quartos)), extrairTextoEditText(descricao));
                    ImovelRepository.getInstance().save(imovel);
                    for (Foto foto : this.fotos) {
                        foto.setImovel(imovel);
                        FotoRepository.getInstance().save(foto);
                    }
                    Toast.makeText(this, "Imóvel salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    setResult(ResultCode.IMOVEL_CADASTRADO_SUCESSO);
                    this.finish();
                } else {
                    Toast.makeText(this, "Por favor preencha todas as imagens", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Ocorreu um erro inesperado! tente novamente", Toast.LENGTH_SHORT).show();
        }
    }
}
