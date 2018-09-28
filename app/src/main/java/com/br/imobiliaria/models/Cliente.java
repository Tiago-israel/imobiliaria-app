package com.br.imobiliaria.models;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Cliente extends SugarRecord<Cliente> implements Serializable{

    private String nome;
    private String email;
    private String telefone;

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

    @Override
    public String toString() {
        return
                "nome: '" + nome + '\'' +
                ", email: '" + email + '\'' +
                ", telefone: '" + telefone + '\'';
    }


}
