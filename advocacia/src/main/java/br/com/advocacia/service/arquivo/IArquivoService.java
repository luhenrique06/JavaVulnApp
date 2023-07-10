package br.com.advocacia.service.arquivo;

import br.com.advocacia.entities.Arquivo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IArquivoService {

    Arquivo findByPath(String path);
    Optional<Arquivo> findById(Long id);
    Arquivo saveEnvioDocumento(MultipartFile arquivo, String nomeArquivo);
    Arquivo saveModelo(MultipartFile arquivo);
    List<Arquivo> saveProcesso(List<MultipartFile> arquivo);
    void deleteById(Long id);
    List<Arquivo> findAllModelo();
    //boolean validExtension(String extension);
    String lerArquivo(String nome);
}
