package br.com.diasnogueira.service.empresa;

import br.com.diasnogueira.entities.Empresa;

import java.util.List;
import java.util.Optional;

public interface IEmpresaService {

    Empresa save(Empresa empresa);
    Empresa update(Empresa empresa);
    void deleteById(Long id);
    Optional<Empresa> findById(Long id);
    List<Empresa> findAll();
}
