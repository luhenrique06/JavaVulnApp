package br.com.advocacia.service.arquivo;

import br.com.advocacia.entities.Arquivo;
import br.com.advocacia.repository.ArquivoRepository;
import br.com.advocacia.service.OSDetection;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ArquivoServiceImpl implements IArquivoService{

    final ArquivoRepository arquivoRepository;

    private String destinoEnvioDocumento = Paths.get("").toAbsolutePath().toString();
    private String destinoModelo = Paths.get("").toAbsolutePath().toString();
    private String destinoProcesso = Paths.get("").toAbsolutePath().toString();

    public ArquivoServiceImpl(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    @Override
    public Arquivo findByPath(String path) {
        return arquivoRepository.findByLinkArquivo(path);
    }

    @Override
    public Optional<Arquivo> findById(Long id) {
        return arquivoRepository.findById(id);
    }

    @Override
    public Arquivo saveEnvioDocumento(MultipartFile arquivo, String nomeArquivo) {
        //pasta precisa existir e precisa ter permissão de escrita
        try{
            String pastaDestino = destinoEnvioDocumento;
            String nome = nomeArquivo;
            Path path = Paths.get(pastaDestino + File.separator + nome);
            Arquivo arqExist = findByPath(path.toString());
            if(arqExist == null){
                Files.copy(arquivo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                Arquivo arq = new Arquivo();
                arq.setLinkArquivo(path.toString());
                arq.setNome(nome);
                return arquivoRepository.save(arq);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Arquivo saveModelo(MultipartFile arquivo) {
        //pasta precisa existir e precisa ter permissão de escrita
        try{
            String pastaDestino = destinoModelo;
            Path path = Paths.get(pastaDestino + File.separator + arquivo.getOriginalFilename());
            Arquivo arqExist = findByPath(path.toString());
            if(arqExist == null){
                Files.copy(arquivo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                Arquivo arq = new Arquivo();
                arq.setLinkArquivo(path.toString());
                arq.setNome(arquivo.getOriginalFilename());
                return arquivoRepository.save(arq);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Arquivo> saveProcesso(List<MultipartFile> arquivo) {
        //pasta precisa existir e precisa ter permissão de escrita
        try{
            String pastaDestino = destinoProcesso;
            List<Arquivo> arquivos = new ArrayList<>();
            for(MultipartFile arqui : arquivo) {
                Path path = Paths.get(pastaDestino + File.separator + arqui.getOriginalFilename());
                Arquivo arqExist = findByPath(path.toString());
                if (arqExist == null) {
                    Files.copy(arqui.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    Arquivo arq = new Arquivo();
                    arq.setLinkArquivo(path.toString());
                    arq.setNome(arqui.getOriginalFilename());
                    arquivos.add(arquivoRepository.save(arq));
                }
            }
            return arquivos;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        arquivoRepository.deleteById(id);
    }

    @Override
    public List<Arquivo> findAllModelo() {
        return arquivoRepository.findAllModelo(destinoModelo);
    }

    @Override
    public String lerArquivo(String nome) {
        StringBuilder strOut = new StringBuilder();
        try{
            String[] command;

            command = new String[]{"sh", "-c", "cat " + nome};
            //command = new String[]{"cmd /c", "type", path};


            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(command);
            int result = proc.waitFor();
            if(result != 0){
                System.out.println("process error");
            }
            InputStream in = (result == 0) ? proc.getInputStream():proc.getErrorStream();
            int c;
            while((c=in.read())!= -1){
                strOut.append((char) c);
            }
            return strOut.toString();

    }catch(Exception e){
        return e.toString();
    }

    }
}
