package br.com.advocacia.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Long id;
    @Column(name = "nome", length = 45)
    private String nome;
    @Column(name = "isAdmin")
    private Boolean isAdmin;
    @Column(name = "login", length = 45)
    private String login;
    @Column(name = "senha", length = 100)
    private String senha;
}
