package br.com.diasnogueira.service.processo;

import br.com.diasnogueira.entities.Processo;
import br.com.diasnogueira.repository.ProcessoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProcessoServiceImpl implements IProcessoService{

    final ProcessoRepository processoRepository;

    public ProcessoServiceImpl(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }

    @Override
    public Processo save(Processo processo){
        return processoRepository.save(processo);
    }

    @Override
    public void deleteById(Long id){
        processoRepository.deleteById(id);
    }

    @Override
    public Optional<Processo> findById(Long id){
        return processoRepository.findById(id);
    }

    @Override
    public List<Processo> findAll(){
        return processoRepository.findAll();
    }
}
