package com.br.imobiliaria.models;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Usuario extends SugarRecord<Usuario> implements Serializable{
    private String nome;
    private String login;
    private String senha;
    private Integer isAdmin;

    public Usuario() {

    }

    public Usuario(String nome, String login, String senha, int isAdmin) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.isAdmin = isAdmin;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}
