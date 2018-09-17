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
    private List<Foto> fotos = new ArrayList<>();
    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private boolean isFoto1 = false, isFoto2 = false, isFoto3 = false, isFoto4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_imovel);
        this.binding();
    }

    public void tirarFoto1(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.isFoto1 = true;
        this.isFoto2 = false;
        this.isFoto3 = false;
        this.isFoto4 = false;
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public void tirarFoto2(View view) {
        this.isFoto1 = false;
        this.isFoto2 = true;
        this.isFoto3 = false;
        this.isFoto4 = false;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public void tirarFoto3(View view) {
        this.isFoto1 = false;
        this.isFoto2 = false;
        this.isFoto3 = true;
        this.isFoto4 = false;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public void tirarFoto4(View view) {
        this.isFoto1 = false;
        this.isFoto2 = false;
        this.isFoto3 = false;
        this.isFoto4 = true;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private void carregarImagemTela(Intent data, ImageView imageView) {
        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Foto foto = new Foto();
                    if (isFoto1) {
                        foto.setIsMain(1);
                        carregarImagemTela(data, foto1);
                    } else if (isFoto2) {
                        carregarImagemTela(data, foto2);
                    } else if (isFoto3) {
                        carregarImagemTela(data, foto3);
                    } else if (isFoto4) {
                        carregarImagemTela(data, foto4);
                    }
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    foto.setArquivo(TratamentoImagem.converterBitMapToArrayBytes(bitmap));
                    this.fotos.add(foto);
                }
            }
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

    private void criarImovel(List<Foto> fotos) {
        Imovel imovel = new Imovel("Casa", 10000.0, "São pedro", 2, "2 banheiros", fotos);
        imovel.save();
        for (Foto foto : fotos) {
            foto.setImovel(imovel);
            foto.save();
        }
    }

    public void salvarImovel(View view) {
        try {
            if (this.validarCamposObrigatorios()) {
                Imovel imovel = new Imovel(extrairTextoEditText(nome), Double.parseDouble(extrairTextoEditText(preco)), extrairTextoEditText(bairro), Integer.parseInt(extrairTextoEditText(quartos)), extrairTextoEditText(descricao));
                ImovelRepository.getInstance().save(imovel);

                for (Foto foto : this.fotos) {
                    foto.setImovel(imovel);
                    FotoRepository.getInstance().save(foto);
                }
                Toast.makeText(this, "Imóvel salvo com sucesso!", Toast.LENGTH_SHORT).show();
                setResult(ResultCode.IMOVEL_CADASTRADO_SUCESSO);
                this.finish();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Ocorreu um erro inesperado! tente novamente", Toast.LENGTH_SHORT).show();
        }
    }
}
