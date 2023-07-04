package br.com.advocacia.controller;

import br.com.advocacia.entities.Empresa;
import br.com.advocacia.entities.EnvioDocumento;
import br.com.advocacia.config.security.ErroDTO;
import br.com.advocacia.service.arquivo.IArquivoService;
import br.com.advocacia.service.empresa.IEmpresaService;
import br.com.advocacia.service.enviodocumento.IEnvioDocumentoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/enviodocumento")
public class EnvioDocumentoController {

    public static final String ENVIODOCUMENTONOTFOUND = "Envio de documento não encontrado!";
    final IEnvioDocumentoService envioDocumentoService;
    final IArquivoService arquivoService;
    final IEmpresaService empresaService;

    public EnvioDocumentoController(IEnvioDocumentoService envioDocumentoService, IArquivoService arquivoService, IEmpresaService empresaService) {
        this.envioDocumentoService = envioDocumentoService;
        this.arquivoService = arquivoService;
        this.empresaService = empresaService;
    }

    @PostMapping()
    public ResponseEntity<Object> save(@RequestBody @Valid EnvioDocumento envioDocumento){
        Optional<Empresa> emp = empresaService.findById(envioDocumento.getEmpresa().getId());
        if(emp.isPresent()){
            envioDocumento.setArquivo(arquivoService.findByPath(envioDocumento.getArquivo().getLinkArquivo()));
            return ResponseEntity.status(HttpStatus.CREATED).body(envioDocumentoService.save(envioDocumento));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(404, "Empresa não encontrada"));
    }

    @PutMapping("/visualizar")
    public ResponseEntity<Object> visualizar(@RequestBody @Valid EnvioDocumento envioDocumento){
        return ResponseEntity.status(HttpStatus.OK).body(envioDocumentoService.updateVisualizado(envioDocumento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "id") Long id){
        Optional<EnvioDocumento> envioDocumentoOptional = envioDocumentoService.findById(id);
        if(envioDocumentoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(404, ENVIODOCUMENTONOTFOUND));
        }
        envioDocumentoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Envio de documento deletado com sucesso!");
    }

    @GetMapping()
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(envioDocumentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id){
        Optional<EnvioDocumento> envioDocumento = envioDocumentoService.findById(id);
        return envioDocumento.<ResponseEntity<Object>>map(documento -> ResponseEntity.status(HttpStatus.OK).body(documento)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO(404, ENVIODOCUMENTONOTFOUND)));
    }

    @GetMapping("/notificacao")
    public ResponseEntity<Object> findAllByVisualizadoIsFalse(){
        return ResponseEntity.status(HttpStatus.OK).body(envioDocumentoService.findAllByVisualizadoIsFalse());
    }

}
