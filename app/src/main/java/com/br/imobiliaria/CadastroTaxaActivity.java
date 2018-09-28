package com.br.imobiliaria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.br.imobiliaria.Interfaces.BaseActivity;
import com.br.imobiliaria.models.Taxa;
import com.br.imobiliaria.repositories.TaxaRepository;

import java.util.List;

public class CadastroTaxaActivity extends AppCompatActivity implements BaseActivity {

    private EditText taxaAteCemMil, taxaAteQuinhentosMil, taxaAcimaQuinhentosMil;
    private Taxa taxa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_taxa);
        this.binding();
        this.buscarTaxaCadastrada();
    }

    private void buscarTaxaCadastrada() {
        List<Taxa> taxas = TaxaRepository.getInstance().findAll();
        if (!taxas.isEmpty()) {
            this.taxa = taxas.get(0);
            this.taxaAteCemMil.setText(String.valueOf(this.taxa.getTaxaAteCemMil()));
            this.taxaAteQuinhentosMil.setText(String.valueOf(this.taxa.getTaxaAteQuinhentosMil()));
            this.taxaAcimaQuinhentosMil.setText(String.valueOf(this.taxa.getTaxaAcimaQuinhentosMil()));
        }
    }

    public void gravarTaxas(View view) {
        if (validarCamposObrigatorios()) {
            if (this.taxa != null) {
                this.atualizarTaxa();
            } else {
                this.novaTaxa();
            }
        }
    }

    private void novaTaxa() {
        this.taxa = new Taxa(Double.parseDouble(extrairTextoEditText(taxaAteCemMil)), Double.parseDouble(extrairTextoEditText(taxaAteQuinhentosMil)), Double.parseDouble(extrairTextoEditText(taxaAcimaQuinhentosMil)));
        TaxaRepository.getInstance().save(this.taxa);
        Toast.makeText(this,"Taxas cadastradas com sucesso!",Toast.LENGTH_SHORT).show();
    }

    private void atualizarTaxa() {
        this.taxa.setTaxaAteCemMil(Double.parseDouble(extrairTextoEditText(taxaAteCemMil)));
        this.taxa.setTaxaAteQuinhentosMil(Double.parseDouble(extrairTextoEditText(taxaAteQuinhentosMil)));
        this.taxa.setTaxaAcimaQuinhentosMil(Double.parseDouble(extrairTextoEditText(taxaAcimaQuinhentosMil)));
        TaxaRepository.getInstance().save(this.taxa);
        Toast.makeText(this,"Taxas editadas com sucesso!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void binding() {
        this.taxaAteCemMil = findViewById(R.id.editTaxaAteCemMil);
        this.taxaAteQuinhentosMil = findViewById(R.id.editTaxaAteQuinhentosMil);
        this.taxaAcimaQuinhentosMil = findViewById(R.id.editTaxaAcimaQuinhentosMil);
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
        if (this.verificarCampoVazio(taxaAteCemMil)) {
            this.escreverMensagemValidacao(taxaAteCemMil, "");
            ehValido = false;
        }
        if (this.verificarCampoVazio(taxaAteQuinhentosMil)) {
            this.escreverMensagemValidacao(taxaAteQuinhentosMil, "");
            ehValido = false;
        }
        if (this.verificarCampoVazio(taxaAcimaQuinhentosMil)) {
            this.escreverMensagemValidacao(taxaAcimaQuinhentosMil, "");
            ehValido = false;
        }
        return ehValido;
    }

    @Override
    public String extrairTextoEditText(EditText campo) {
        return campo.getText().toString().trim();
    }
}
