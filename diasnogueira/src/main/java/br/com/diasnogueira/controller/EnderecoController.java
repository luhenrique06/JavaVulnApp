package br.com.diasnogueira.controller;

import br.com.diasnogueira.config.security.ErroDTO;
import br.com.diasnogueira.entities.Endereco;
import br.com.diasnogueira.service.endereco.EnderecoService;
import br.com.diasnogueira.service.escritorio.EscritorioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/endereco")
public class EnderecoController {

    public static final String ENDERECONOTFOUND = "Endereço não encontrado!";
    final EnderecoService enderecoService;
    final EscritorioService escritorioService;

    public EnderecoController(EnderecoService enderecoService, EscritorioService escritorioService) {
        this.enderecoService = enderecoService;
        this.escritorioService = escritorioService;
    }

    @PostMapping()
    public ResponseEntity<Object> saveEndereco(@RequestBody @Valid Endereco endereco){
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.save(endereco));
    }
    @PostMapping("/{id}")
    public ResponseEntity<Object> saveEnderecoAndEscritorio(@PathVariable(value = "id") Long idEscritorio, @RequestBody @Valid Endereco endereco){
        return ResponseEntity.status(HttpStatus.CREATED).body(escritorioService.addEndereco(idEscritorio, endereco));
    }

    @GetMapping()
    public ResponseEntity<Object> findAllEnderecos(){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findEnderecoById(@PathVariable(value = "id") Long id){
        Optional<Endereco> enderecoOptional = enderecoService.findById(id);
        return enderecoOptional.<ResponseEntity<Object>>map(endereco -> ResponseEntity.status(HttpStatus.OK).body(endereco)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(404,ENDERECONOTFOUND)));
    }

    @PutMapping()
    public ResponseEntity<Object> updateByEndereco(@RequestBody @Valid Endereco endereco){
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.update(endereco));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "id") Long id){
        Optional<Endereco> enderecoOptional = enderecoService.findById(id);
        if(enderecoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(404,ENDERECONOTFOUND));
        }
        enderecoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Endereço deletado com sucesso!");
    }
}
