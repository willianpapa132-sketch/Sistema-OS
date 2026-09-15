package jpa.repository.demo.servico.service;

import jpa.repository.demo.servico.dto.ServicoRequestDTO;
import jpa.repository.demo.servico.dto.ServicoResponseDTO;
import jpa.repository.demo.servico.entity.Servico;
import jpa.repository.demo.ordemdeservico.itemservico.repository.ItemServicoRepository;
import jpa.repository.demo.servico.repository.ServicoRepository;
import jpa.repository.demo.handler.BusinessException;
import jpa.repository.demo.handler.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicoService {
    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    ItemServicoRepository itemServicoRepository;

    public ServicoResponseDTO salvarServico(ServicoRequestDTO servicoRequestDTO) {
        Servico servico = toEntity(servicoRequestDTO);
        Servico servicoSalvo = servicoRepository.save(servico);
        return toResponseDTO(servicoSalvo);
    }
    public ServicoResponseDTO editarServico(ServicoRequestDTO servicoRequestDTO, Long id) {

        if(id == null ){
            throw new BusinessException("não deve ser informado um id nulo");
        }

        buscarServico(id);
        Servico servicoNovo = toEntity(servicoRequestDTO);
        servicoNovo.setId(id);
        Servico servicoSalvo = servicoRepository.save(servicoNovo);
        return toResponseDTO(servicoSalvo);

    }
    public String deletarServico(Long id){
        if (id == null) {
            throw new BusinessException("Nao deve ser informado um id nulo");
        }

        Servico servicoLocalizo = buscarServicoId(id);
        long itensVinculados = itemServicoRepository.countByServico_Id(id);

        if ( itensVinculados > 0) {
            throw new BusinessException(
                    "Nao e possivel excluir este servico porque ele esta vinculado a "
                            + itensVinculados + " item(ns) de servico."
            );
        }

        servicoRepository.delete(servicoLocalizo);
        return "Servico removido com sucesso";
    }
    public Servico buscarServicoId(Long id){
        return servicoRepository.findById(id).orElseThrow(()-> new NotFoundException("não foi localizado esse serviço"));
    }
    public ServicoResponseDTO buscarServico(Long id){
        Servico servicoLocalizado = servicoRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("não foi localizado esse serviço"));

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

    public Servico toEntity(ServicoRequestDTO servicoRequestDTO){
        return new Servico(
                servicoRequestDTO.getDescricao(), servicoRequestDTO.getValor()
        );
    }

    public ServicoResponseDTO toResponseDTO(Servico servico) {
        return new ServicoResponseDTO(
                servico.getId(),servico.getDescricao(),servico.getValor()
        );
    }

}
