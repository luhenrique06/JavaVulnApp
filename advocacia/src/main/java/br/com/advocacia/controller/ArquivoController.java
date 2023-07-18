package br.com.advocacia.controller;

import br.com.advocacia.entities.Arquivo;
import br.com.advocacia.config.security.ErroDTO;
import br.com.advocacia.service.arquivo.IArquivoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/arquivo")
public class ArquivoController {

    final IArquivoService arquivoService;

    public ArquivoController(IArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @PostMapping("/modelo")
    public ResponseEntity<Object> uploadModelo(@RequestParam(name = "arquivo") MultipartFile arquivo){
        Arquivo arq = arquivoService.saveModelo(arquivo);
        if(arq!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(arq);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroDTO(400, "Extensão não permita ou talvez já exista um arquivo de processo com esse nome"));
    }
    @PostMapping("/processo")
    public ResponseEntity<Object> uploadProcesso(@RequestParam(name = "arquivo") List<MultipartFile> arquivo){
        List<Arquivo> arq = new ArrayList<>(arquivoService.saveProcesso(arquivo));
        if(!arq.isEmpty()){
            return ResponseEntity.status(HttpStatus.CREATED).body(arq);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroDTO(400, "Extensão não permita ou talvez já exista um arquivo de processo com esse nome"));
    }
    @PostMapping("/enviodocumento")

    public ResponseEntity<Object> uploadEnvioDocumento(@RequestParam(name = "arquivo") MultipartFile arquivo, @RequestParam(name = "nome") String nome){
        Arquivo arq = arquivoService.saveEnvioDocumento(arquivo, nome);
        if(arq!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(arq);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Extensão inválida!");
    }

    @GetMapping()
    public ResponseEntity<Object> findByPath(@RequestBody @Valid String path){
        return ResponseEntity.status(HttpStatus.OK).body(arquivoService.findByPath(path));
    }
    @GetMapping("/modelo")
    public ResponseEntity<Object> findAllModelo(){
        return ResponseEntity.status(HttpStatus.OK).body(arquivoService.findAllModelo());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id){
        Optional<Arquivo> arquivo = arquivoService.findById(id);
        return arquivo.<ResponseEntity<Object>>map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(404, "Arquivo não encontrado!")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "id") Long id){
        Optional<Arquivo> arquivo = arquivoService.findById(id);
        if(arquivo.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Arquivo não encontrado!");
        }
        arquivoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Arquivo deletado com sucesso!");
    }

    @GetMapping("/ler/{arquivo}")
    public ResponseEntity<Object> lerArquivo(@PathVariable(value = "arquivo") String arquivo){
       
        return ResponseEntity.status(HttpStatus.OK).body(arquivoService.lerArquivo(arquivo));
    }
}
