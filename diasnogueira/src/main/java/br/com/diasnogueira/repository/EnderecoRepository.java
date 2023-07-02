package br.com.diasnogueira.repository;

import br.com.diasnogueira.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    Endereco save(Endereco endereco);

    void deleteById(Long id);

    Optional<Endereco> findById(Long id);

    List<Endereco> findAll();
}
