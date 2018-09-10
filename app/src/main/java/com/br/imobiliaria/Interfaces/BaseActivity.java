package com.br.imobiliaria.Interfaces;

import android.widget.EditText;

public interface BaseActivity {
    void binding();
    void escreverMensagemValidacao(EditText campo, String nomeCampo);
    boolean verificarCampoVazio(EditText campo);
    boolean validarCamposObrigatorios();
    String extrairTextoEditText(EditText campo);
}
