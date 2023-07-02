package br.com.diasnogueira.repository;

import br.com.diasnogueira.entities.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

    Arquivo findByLinkArquivo(String linkArquivo);
    Optional<Arquivo> findById(Long id);
    void deleteById(Long id);
    @Query("select a from Arquivo a where a.linkArquivo like CONCAT('%',?1,'%')")
    List<Arquivo> findAllModelo(String pathModelo);
}
