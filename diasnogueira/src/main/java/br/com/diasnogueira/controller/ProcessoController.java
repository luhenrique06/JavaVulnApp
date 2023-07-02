package br.com.diasnogueira.controller;

import br.com.diasnogueira.config.security.ErroDTO;
import br.com.diasnogueira.entities.Processo;
import br.com.diasnogueira.service.processo.ProcessoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/processo")
public class ProcessoController {

    public static final String PROCESSONOTFOUND = "Processo não encontrado!";

    final ProcessoServiceImpl processoService;

    public ProcessoController(ProcessoServiceImpl processoService) {
        this.processoService = processoService;
    }

    @PostMapping()
    public ResponseEntity<Object> saveProcesso(@RequestBody @Valid Processo processo){
        return ResponseEntity.status(HttpStatus.CREATED).body(processoService.save(processo));
    }

    @GetMapping()
    public ResponseEntity<Object> findAllProcessos(){
        return ResponseEntity.status(HttpStatus.OK).body(processoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findProcessoById(@PathVariable(value = "id") Long id){
        Optional<Processo> processoOptional = processoService.findById(id);
        return processoOptional.<ResponseEntity<Object>>map(processo -> ResponseEntity.status(HttpStatus.OK).body(processo)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(404, PROCESSONOTFOUND)));
    }

    @PutMapping()
    public ResponseEntity<Object> updateByProcesso(@RequestBody @Valid Processo processo){
        return ResponseEntity.status(HttpStatus.OK).body(processoService.save(processo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProcessoById(@PathVariable(value = "id") Long id){
        Optional<Processo> processoOptional = processoService.findById(id);
        if (processoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(404, PROCESSONOTFOUND));
        }
        processoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Processo deletado com sucesso!");
    }
}
