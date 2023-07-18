package br.com.advocacia.service.empresa;

import br.com.advocacia.entities.Empresa;

import java.util.List;
import java.util.Optional;

public interface IEmpresaService {

    Empresa save(Empresa empresa);
    Empresa update(Empresa empresa);
    void deleteById(Long id);
    Optional<Empresa> findById(Long id);
    List<Empresa> findAll();
}
