package com.br.imobiliaria.models;

public class Foto {

    private byte arquivo;
    private int isMain;

    public Foto() {
    }

    public byte getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte arquivo) {
        this.arquivo = arquivo;
    }

    public int getIsMain() {
        return isMain;
    }

    public void setIsMain(int isMain) {
        this.isMain = isMain;
    }
}
