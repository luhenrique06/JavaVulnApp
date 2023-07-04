package br.com.advocacia.controller;

import br.com.advocacia.entities.Usuario;
import br.com.advocacia.config.security.AuthToken;
import br.com.advocacia.config.security.ErroDTO;
import br.com.advocacia.config.security.TokenUtil;
import br.com.advocacia.service.usuario.IUsuarioService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.Duration;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/usuario")
public class UsuarioController {

    public static final String USUARIONOTFOUND = "Usuário não encontrado!";
    final IUsuarioService usuarioService;
    final PasswordEncoder passwordEncoder;
  




    public UsuarioController(IUsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }





    @PostMapping("/login")
    public ResponseEntity<AuthToken> realizarLogin(@RequestBody @Valid Usuario usuario){
        Optional<Usuario> u = usuarioService.findByLogin(usuario.getLogin());

        if(u.isPresent() && usuarioService.verifyPassword(usuario.getSenha(), u.get())){
            return ResponseEntity.ok(TokenUtil.encodeToken(usuario));
        }
        
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping()
    public ResponseEntity<Object> save(@RequestBody @Valid Usuario usuario){
        if(usuarioService.existsByLogin(usuario.getLogin())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Login indisponível");
        }
        String passwordCripto = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(passwordCripto);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @PutMapping()
    public ResponseEntity<Object> updatePassword(@RequestBody @Valid Usuario usuario){
        Optional<Usuario> u = usuarioService.findByLogin(usuario.getLogin());
        if(u.isPresent()){
            u.get().setSenha(passwordEncoder.encode(usuario.getSenha()));
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.save(u.get()));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(404, USUARIONOTFOUND));
        }
    }

    @GetMapping()
    public ResponseEntity<Object> findAllUsuario(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findUsuarioById(@PathVariable(value = "id") Long id){
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        return usuarioOptional.<ResponseEntity<Object>>map(usuario -> ResponseEntity.status(HttpStatus.OK).body(usuario)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(404, USUARIONOTFOUND)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUsuarioById(@PathVariable(value = "id") Long id){
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if(usuarioOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(404, USUARIONOTFOUND));
        }
        usuarioService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso!");
    }

}