package br.com.advocacia.controller.DTOs;



public class UsuarioDTO {

    private Long id;
    private String nome;
    private String login;
    private String senha;
    private String token;


    public UsuarioDTO(Long id, String nome, String login) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = "";
        this.token = "";

    }


    public UsuarioDTO(String login, String senha, String token) {
        this.login = login;
        this.senha = senha;
        this.token = token;

    }

    // getters e setters (opcional)
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }


    public void setSenha(String senha) {
        this.senha = senha;
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

    

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
