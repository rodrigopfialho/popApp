package com.example.popmovies.model;

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String caminhhoFoto;

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCaminhhoFoto() {
        return caminhhoFoto;
    }

    public void setCaminhhoFoto(String caminhhoFoto) {
        this.caminhhoFoto = caminhhoFoto;
    }
}
