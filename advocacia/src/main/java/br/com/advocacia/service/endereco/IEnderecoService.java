package br.com.advocacia.service.endereco;

import br.com.advocacia.entities.Endereco;

import java.util.List;
import java.util.Optional;

public interface IEnderecoService {

    Endereco save(Endereco endereco);
    Endereco update(Endereco endereco);
    void deleteById(Long id);
    Optional<Endereco> findById(Long id);
    List<Endereco> findAll();

}
