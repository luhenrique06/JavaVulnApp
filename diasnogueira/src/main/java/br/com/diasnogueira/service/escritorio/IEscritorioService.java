package br.com.diasnogueira.service.escritorio;

import br.com.diasnogueira.entities.Endereco;
import br.com.diasnogueira.entities.Escritorio;

import java.util.List;
import java.util.Optional;

public interface IEscritorioService {

    Escritorio save(Escritorio escritorio);
    Escritorio update(Escritorio escritorio);
    void deleteById(Long id);
    Optional<Escritorio> findById(Long id);
    List<Escritorio> findAll();
    Escritorio addEndereco(Long idEscritorio, Endereco endereco);
}