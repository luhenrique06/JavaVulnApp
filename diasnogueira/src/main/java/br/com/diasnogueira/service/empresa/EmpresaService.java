package br.com.diasnogueira.service.empresa;

import br.com.diasnogueira.entities.Empresa;
import br.com.diasnogueira.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService implements IEmpresaService{

    final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public Empresa save(Empresa empresa){
        return empresaRepository.save(empresa);
    }
    public Empresa update(Empresa empresa){
        return empresaRepository.save(empresa);
    }

    public void deleteById(Long id){
        empresaRepository.deleteById(id);
    }

    public Optional<Empresa> findById(Long id){
        return empresaRepository.findById(id);
    }

    public List<Empresa> findAll(){
        return empresaRepository.findAll();
    }
}
