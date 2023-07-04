package br.com.advocacia.controller;

import br.com.advocacia.entities.Empresa;
import br.com.advocacia.service.empresa.EmpresaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/empresa")
public class EmpresaController {
    public static final String EMPRESANOTFOUND = "Empresa não encontrada!";

    final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping()
    public ResponseEntity<Object> saveEmpresa(@RequestBody @Valid Empresa empresa){
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.save(empresa));
    }

    @GetMapping()
    public ResponseEntity<Object> findAllEmpresas(){
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findEmpresaById(@PathVariable(value = "id") Long id){
        Optional<Empresa> empresaOptional = empresaService.findById(id);
        return empresaOptional.<ResponseEntity<Object>>map(empresa -> ResponseEntity.status(HttpStatus.OK).body(empresa)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(EMPRESANOTFOUND));
    }

    @PutMapping()
    public ResponseEntity<Object> updateByEmpresa(@RequestBody @Valid Empresa empresa){
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.update(empresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "id") Long id){
        Optional<Empresa> empresaOptional = empresaService.findById(id);
        if(empresaOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(EMPRESANOTFOUND);
        }
        empresaService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Empresa deletada com sucesso!");
    }
}
