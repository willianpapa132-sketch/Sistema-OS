package jpa.repository.demo.Aplication.service;

import jpa.repository.demo.domain.dto.ServicoRequestDTO;
import jpa.repository.demo.domain.dto.ServicoResponseDTO;
import jpa.repository.demo.domain.entity.Servico;
import jpa.repository.demo.domain.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {
    @Autowired
    ServicoRepository servicoRepository;

    public ServicoResponseDTO salvarServico(ServicoRequestDTO servicoRequestDTO) throws Exception{
        if (servicoRequestDTO.getValor() == null || servicoRequestDTO.getValor() < 0){
            throw new Exception ("valor não pode ser menor que zero ");
        }
        Servico servico = toEntity(servicoRequestDTO);
        Servico servicoSalvo = servicoRepository.save(servico);
        return toResponseDTO(servicoSalvo);
    }
    public ServicoResponseDTO editarServico(ServicoRequestDTO servicoRequestDTO, Long id) throws Exception{
        Servico servicoNovo = toEntity(servicoRequestDTO);
        servicoNovo.setId(id);
        if (servicoRequestDTO.getValor() == null || servicoRequestDTO.getValor() < 0){
            throw new Exception("Não pode ter valor negativo");
        }
        Servico servicoSalvo = servicoRepository.save(servicoNovo);
        return toResponseDTO(servicoSalvo);

    }
    public String deletarServico(Long id)throws Exception{
        Servico servicoLocalizo =servicoRepository.findById(id).orElseThrow(()-> new Exception("não foi localizado esse serviço"));
        servicoRepository.delete(servicoLocalizo);
        return "Servico removido com sucesso";
    }
    public Servico buscarServicoId(Long id)throws Exception{
        return servicoRepository.findById(id).orElseThrow(()-> new Exception("não foi localizado esse serviço"));
    }
    public ServicoResponseDTO buscarServico(Long id)throws Exception{
        Servico servicoLocalizado = servicoRepository.findById(id)
                .orElseThrow(()-> new Exception("não foi localizado esse serviço"));

        return new ServicoResponseDTO(
                servicoLocalizado.getId(), servicoLocalizado.getDescricao(), servicoLocalizado.getValor()
        );
    }
    public List <ServicoResponseDTO> listarServicos(){
        List<Servico> servicos = servicoRepository.findAll();
        return servicos.stream().map(servico -> new ServicoResponseDTO(
                servico.getId(),servico.getDescricao(),servico.getValor()
        )).toList();
    }

    public Servico toEntity(ServicoRequestDTO servicoRequestDTO) throws Exception{
        return new Servico(
                servicoRequestDTO.getDescricao(), servicoRequestDTO.getValor()
        );
    }

    public ServicoResponseDTO toResponseDTO(Servico servico) throws Exception{
        return new ServicoResponseDTO(
                servico.getId(),servico.getDescricao(),servico.getValor()
        );
    }

}
