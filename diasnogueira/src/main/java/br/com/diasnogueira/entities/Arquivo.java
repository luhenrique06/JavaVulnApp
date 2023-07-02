package br.com.diasnogueira.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "arquivo")
public class Arquivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArquivo")
    private Long id;
    private String nome;
    private String linkArquivo;
    @ManyToOne
    @JoinColumn(name = "idProcesso")
    private Processo processo;
}
