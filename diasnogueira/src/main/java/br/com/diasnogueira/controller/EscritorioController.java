package br.com.diasnogueira.controller;

import br.com.diasnogueira.config.security.ErroDTO;
import br.com.diasnogueira.entities.Escritorio;
import br.com.diasnogueira.service.escritorio.EscritorioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/escritorio")
public class EscritorioController {

    public static final String ESCRITORIONOTFOUND = "Escritório não encontrado!";
    final EscritorioService escritorioService;

    public EscritorioController(EscritorioService escritorioService) {
        this.escritorioService = escritorioService;
    }

    @PostMapping()
    public ResponseEntity<Object> saveEscritorio(@RequestBody @Valid Escritorio escritorio){
        return ResponseEntity.status(HttpStatus.CREATED).body(escritorioService.save(escritorio));
    }

    @GetMapping()
    public ResponseEntity<Object> findAllEscritorios(){
        return ResponseEntity.status(HttpStatus.OK).body(escritorioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findEscritorioById(@PathVariable(value = "id") Long id){
        Optional<Escritorio> escritorioOptional = escritorioService.findById(id);
        return escritorioOptional.<ResponseEntity<Object>>map(escritorio -> ResponseEntity.status(HttpStatus.OK).body(escritorio)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ESCRITORIONOTFOUND));
    }

    @PutMapping()
    public ResponseEntity<Object> updateByEscritorio(@RequestBody @Valid Escritorio escritorio){
        return ResponseEntity.status(HttpStatus.CREATED).body(escritorioService.update(escritorio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "id") Long id){
        Optional<Escritorio> escritorioOptional = escritorioService.findById(id);
        if(escritorioOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(404,ESCRITORIONOTFOUND));
        }
        escritorioService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Escritório deletado com sucesso!");
    }

}
