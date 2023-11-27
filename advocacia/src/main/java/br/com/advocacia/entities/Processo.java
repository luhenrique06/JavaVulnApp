package br.com.advocacia.entities;

import br.com.advocacia.entities.enums.TipoProcesso;
import jakarta.persistence.*;
import lombok.Data;


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
    private TipoProcesso tipoProcesso;
}
