package br.com.diasnogueira.entities;

import br.com.diasnogueira.entities.enums.Requerimento;
import br.com.diasnogueira.entities.enums.TipoProcesso;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "enviodocumento")
public class EnvioDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEnvioDocumento")
    private Long id;
    private TipoProcesso tipoProcesso;
    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;
    private Requerimento requerimento;
    private String nomeCredor;
    private String cpfCnpj;
    private String representanteLegal;
    private String documentoRepresentante;
    private String email;
    private String whatsapp;
    private String obs;
    @OneToOne
    private Arquivo arquivo;
    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean visualizado;

}
