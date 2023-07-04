package br.com.advocacia.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "empresa")
@JsonIgnoreProperties("processo")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmpresa")
    private Long id;
    private String nome;
    private String resumo;
    @OneToMany(mappedBy = "empresa")
    private List<Processo> processo;
}
