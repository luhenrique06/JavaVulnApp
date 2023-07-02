package br.com.diasnogueira.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "endereco")
@JsonIgnoreProperties("escritorio")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEndereco")
    private Long id;
    private String logradouro;
    private Long numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    @ManyToOne
    @JoinColumn(name = "idEscritorio")
    private Escritorio escritorio;
}
