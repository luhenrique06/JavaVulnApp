package br.com.advocacia.entities;

import br.com.advocacia.entities.enums.TipoProcesso;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "processo")
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProcesso")
    private Long id;
    private Long numProcesso;
    private String comarca;
    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;
    @OneToMany(mappedBy = "processo")
    private List<Arquivo> pecasProcessuais;
    @OneToMany(mappedBy = "processo")
    private List<Arquivo> prestacaoContas;
    private TipoProcesso tipoProcesso;
}
