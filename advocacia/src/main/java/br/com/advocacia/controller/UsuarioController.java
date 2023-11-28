package br.com.advocacia.controller;

import br.com.advocacia.entities.Usuario;
import br.com.advocacia.config.security.AuthToken;
import br.com.advocacia.config.security.ErroDTO;
import br.com.advocacia.config.security.TokenUtil;
import br.com.advocacia.controller.DTOs.UsuarioDTO;
import br.com.advocacia.service.usuario.IUsuarioService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import javax.validation.Valid;

import java.security.Principal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
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
    public ResponseEntity<Object> realizarLogin(@RequestBody @Valid Usuario usuario){
        Optional<Usuario> u = usuarioService.findByLogin(usuario.getLogin());

        if(u.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("Usuário não existe!");
        }
        
        if(u.isPresent() && usuarioService.verifyPassword(usuario.getSenha(), u.get())){
            String token = new TokenUtil().encodeToken(u);
            return ResponseEntity.ok(
                new UsuarioDTO(u.get().getId(), u.get().getLogin(), u.get().getNome() , u.get().getIsAdmin(), token));
        }
        
        return ResponseEntity.status(HttpStatus.OK).body("Senha Incorreta!");
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
        List<Usuario> usuarios = usuarioService.findAll();
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for(Usuario usuario: usuarios){
            UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getIsAdmin());
            usuarioDTOs.add(usuarioDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuarioDTOs);
    }

    ///////CORREÇÃO DO IDOR AQUI
    ///////////COLOCA O PRINCIPAL, FAZ O IF E AJUSTA OS RETORNOS
    @GetMapping("/{id}")
    public ResponseEntity<Object> findUsuarioById(@PathVariable(value = "id") Long id, Authentication principal){
        String logado = principal.getPrincipal().toString();
        Optional<Usuario> usuarioOptional = usuarioService.findByLogin(logado);
        if(usuarioOptional.get().getId().equals(id))
            return ResponseEntity.status(HttpStatus.OK).body(usuarioOptional.get());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autorizado!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUsuarioById(@PathVariable(value = "id") Long id, Authentication auth)
    {
        if (!"admin".equals(auth.getCredentials())) 
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario não autorizado!");
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if(usuarioOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(404, USUARIONOTFOUND));
        }
        //usuarioService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado com sucesso!");
    }

}