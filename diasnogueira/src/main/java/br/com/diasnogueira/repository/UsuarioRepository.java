package br.com.diasnogueira.repository;

import br.com.diasnogueira.entities.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
    boolean existsByLogin(String login);
    void deleteById(Long id);
    Usuario save(Usuario usuario);
    List<Usuario> findAll();
}
