package br.com.diasnogueira.service.endereco;

import br.com.diasnogueira.entities.Endereco;
import br.com.diasnogueira.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService implements IEnderecoService{

    final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public Endereco save(Endereco endereco){
        return enderecoRepository.save(endereco);
    }

    @Override
    public Endereco update(Endereco endereco){
        return enderecoRepository.save(endereco);
    }

    @Override
    public void deleteById(Long id){
        enderecoRepository.deleteById(id);
    }

    @Override
    public Optional<Endereco> findById(Long id){
        return enderecoRepository.findById(id);
    }

    @Override
    public List<Endereco> findAll(){
        return enderecoRepository.findAll();
    }
}
