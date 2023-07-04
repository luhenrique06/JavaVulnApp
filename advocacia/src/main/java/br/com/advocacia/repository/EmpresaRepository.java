package br.com.advocacia.repository;

import br.com.advocacia.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Empresa save(Empresa endereco);

    void deleteById(Long id);

    Optional<Empresa> findById(Long id);

    List<Empresa> findAll();
}
