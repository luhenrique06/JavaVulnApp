package br.com.advocacia.service.escritorio;

import br.com.advocacia.entities.Endereco;
import br.com.advocacia.entities.Escritorio;
import br.com.advocacia.repository.EscritorioRepository;
import br.com.advocacia.service.endereco.IEnderecoService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EscritorioService implements IEscritorioService{

    final EscritorioRepository escritorioRepository;
    final IEnderecoService enderecoService;

    public EscritorioService(EscritorioRepository escritorioRepository,  IEnderecoService enderecoService) {
        this.escritorioRepository = escritorioRepository;
        this.enderecoService = enderecoService;
    }

    @Override
    public Escritorio save(Escritorio escritorio){
        Escritorio e = escritorioRepository.save(escritorio);
        for(Endereco end: escritorio.getEndereco()){
            end.setEscritorio(e);
            enderecoService.save(end);
        }
        return e;
    }

    @Override
    public Escritorio update(Escritorio escritorio){
        return escritorioRepository.save(escritorio);
    }

    @Override
    public void deleteById(Long id){
        escritorioRepository.deleteById(id);
    }

    @Override
    public Optional<Escritorio> findById(Long id){
        return escritorioRepository.findById(id);
    }

    @Override
    public List<Escritorio> findAll(){
        return escritorioRepository.findAll();
    }

    @Override
    public Escritorio addEndereco(Long idEscritorio, Endereco endereco) {
        Optional<Escritorio> escritorioOptional = escritorioRepository.findById(idEscritorio);
        escritorioOptional.ifPresent(endereco::setEscritorio);
        enderecoService.save(endereco);
        return escritorioOptional.get();
    }
}
