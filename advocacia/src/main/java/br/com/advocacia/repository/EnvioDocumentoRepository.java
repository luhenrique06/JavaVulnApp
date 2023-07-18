package br.com.advocacia.repository;

import br.com.advocacia.entities.EnvioDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnvioDocumentoRepository extends JpaRepository<EnvioDocumento, Long> {

    EnvioDocumento save(EnvioDocumento envioDocumento);
    void deleteById(Long id);
    Optional<EnvioDocumento> findById(Long id);
    List<EnvioDocumento> findAll();
    List<EnvioDocumento> findAllByVisualizadoIsFalse();
}
