package com.br.imobiliaria.models;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;

public class Foto extends SugarRecord<Foto> implements Serializable {

    private String arquivo;
    private int isMain = 0;
    private Imovel imovel;
    @Ignore
    private String idAux;

    public Foto() {
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public int getIsMain() {
        return isMain;
    }

    public void setIsMain(int isMain) {
        this.isMain = isMain;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public String getIdAux() {
        return this.idAux;
    }

    public void setIdAux(String idAux) {
        this.idAux = idAux;
    }
}
