package br.com.diasnogueira.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "escritorio")
public class Escritorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEscritorio")
    private Long id;
    @OneToMany(mappedBy = "escritorio", cascade = CascadeType.ALL)
    private List<Endereco> endereco;
    private String telefone;
    private String celular;
    private String email;
    private String dadosEmpresa;
}
