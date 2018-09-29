package com.br.imobiliaria.models;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.List;

public class Cliente extends SugarRecord<Cliente> implements Serializable{

    @Ignore
    private String idStr;
    private String nome;
    private String email;
    private String telefone;
    @Ignore
    private List<Imovel>imoveis;

    public Cliente() {

    }

    public Cliente(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Imovel> getImoveis() {
        return imoveis;
    }

    public void setImoveis(List<Imovel> imoveis) {
        this.imoveis = imoveis;
    }

    @Override
    public String toString() {
        return
                "nome: '" + nome + '\'' +
                ", email: '" + email + '\'' +
                ", telefone: '" + telefone + '\'';
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }
}
