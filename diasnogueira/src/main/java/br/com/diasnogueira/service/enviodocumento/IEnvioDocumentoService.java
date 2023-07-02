package br.com.diasnogueira.service.enviodocumento;

import br.com.diasnogueira.entities.EnvioDocumento;

import java.util.List;
import java.util.Optional;

public interface IEnvioDocumentoService {

    EnvioDocumento save(EnvioDocumento envioDocumento);
    void deleteById(Long id);
    Optional<EnvioDocumento> findById(Long id);
    List<EnvioDocumento> findAll();
    List<EnvioDocumento> findAllByVisualizadoIsFalse();
    Object updateVisualizado(EnvioDocumento envioDocumento);
}
