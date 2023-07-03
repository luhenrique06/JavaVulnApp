package br.com.diasnogueira.service.usuario;


import br.com.diasnogueira.config.security.Pass;
import br.com.diasnogueira.entities.Usuario;
import br.com.diasnogueira.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    final UsuarioRepository usuarioRepository;
    final Pass pass;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, Pass pass) {
        this.usuarioRepository = usuarioRepository;
        this.pass = pass;
    }

    @Override
    public Optional<Usuario> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    @Override
    public boolean existsByLogin(String login) {
        return usuarioRepository.existsByLogin(login);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public boolean verifyPassword(String password, Usuario usuario){
        return pass.matches(password, usuario.getSenha());
    }
}