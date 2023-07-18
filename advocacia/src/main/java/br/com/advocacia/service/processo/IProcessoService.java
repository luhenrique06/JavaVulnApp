package br.com.advocacia.service.processo;

import br.com.advocacia.entities.Processo;

import java.util.List;
import java.util.Optional;

public interface IProcessoService {

    Processo save(Processo processo);
    void deleteById(Long id);
    Optional<Processo> findById(Long id);
    List<Processo> findAll();
}
