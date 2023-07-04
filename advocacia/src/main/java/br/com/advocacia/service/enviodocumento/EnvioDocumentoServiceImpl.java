package br.com.advocacia.service.enviodocumento;

import br.com.advocacia.entities.EnvioDocumento;
import br.com.advocacia.repository.EnvioDocumentoRepository;
import br.com.advocacia.config.security.ErroDTO;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvioDocumentoServiceImpl implements IEnvioDocumentoService {

    final EnvioDocumentoRepository envioDocumentoRepository;

    public EnvioDocumentoServiceImpl(EnvioDocumentoRepository envioDocumentoRepository) {
        this.envioDocumentoRepository = envioDocumentoRepository;
    }

    @Override
    public EnvioDocumento save(EnvioDocumento envioDocumento) {
        return envioDocumentoRepository.save(envioDocumento);
    }

    @Override
    public void deleteById(Long id) {
        envioDocumentoRepository.deleteById(id);
    }

    @Override
    public Optional<EnvioDocumento> findById(Long id) {
        return envioDocumentoRepository.findById(id);
    }

    @Override
    public List<EnvioDocumento> findAll() {
        return envioDocumentoRepository.findAll();
    }

    @Override
    public List<EnvioDocumento> findAllByVisualizadoIsFalse() {
        return envioDocumentoRepository.findAllByVisualizadoIsFalse();
    }

    @Override
    public Object updateVisualizado(EnvioDocumento envioDocumento) {
        Optional<EnvioDocumento> e = envioDocumentoRepository.findById(envioDocumento.getId());
        if(e.isPresent()){
            e.get().setVisualizado(true);
            return envioDocumentoRepository.save(e.get());
        }
        return new ErroDTO(404, "Envio de documento não encontrado!");
    }

}
