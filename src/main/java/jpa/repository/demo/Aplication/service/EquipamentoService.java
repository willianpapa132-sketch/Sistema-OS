package jpa.repository.demo.Aplication.service;

import jpa.repository.demo.domain.dto.EquipamentoRequestDTO;
import jpa.repository.demo.domain.dto.EquipamentoResponseDTO;
import jpa.repository.demo.domain.entity.Equipamento;
import jpa.repository.demo.domain.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipamentoService {

    @Autowired
    EquipamentoRepository equipamentoRepository;

    @Autowired
    ClienteService clienteService;


    public EquipamentoResponseDTO salvarEquipamento(EquipamentoRequestDTO equipamentoRequestDTO) throws Exception {
        Equipamento equipamento = toEntity(equipamentoRequestDTO);
        Equipamento equipamentoSalvo = equipamentoRepository.save(equipamento);
        return toResponseDTO(equipamentoSalvo);
    }

    public EquipamentoResponseDTO  alterarStatus(EquipamentoResponseDTO equipamentoResponseDTO)throws Exception{
        Equipamento equipamentoLocalizado = buscarEquipamentoId(equipamentoResponseDTO.getId());
        equipamentoLocalizado.setEmmanutencao(equipamentoResponseDTO.getEmmanutencao());
        Equipamento equipamentoSalvo = equipamentoRepository.save(equipamentoLocalizado);
        return toResponseDTO(equipamentoSalvo);
    }

    public List<EquipamentoResponseDTO> listarEquipamentos(){
        List<Equipamento> equipamentos = equipamentoRepository.findAll();
        return equipamentos.stream().map(equipamento ->  new EquipamentoResponseDTO(
                equipamento.getId(),clienteService.toResponseDTO(equipamento.getCliente()), equipamento.getModelo(),
                equipamento.getAno(), equipamento.getMarca(),
                equipamento.getEmmanutencao()
        )).toList();
    }

    public String deletarEquipamento(Long id)throws Exception{
        Equipamento equipamentoLocalizo = equipamentoRepository.findById(id).orElseThrow(()-> new Exception("não foi localizado esse equipamento"));
        equipamentoRepository.delete(equipamentoLocalizo);
        return "equipamento deletado com sucesso";
    }

    public Equipamento buscarEquipamentoId (Long id)throws Exception {
        return equipamentoRepository.findById(id).orElseThrow(()-> new Exception("não foi localizado esse equipamento"));
    }

    public EquipamentoResponseDTO buscarEquipamento (Long id)throws Exception{
        Equipamento equipamentoLocalizado = buscarEquipamentoId(id);
        return toResponseDTO(equipamentoLocalizado);
    }

    public Equipamento toEntity(EquipamentoRequestDTO equipamentoRequestDTO)throws Exception{
        return new Equipamento(
                clienteService.buscarClienteId(equipamentoRequestDTO.getClienteId()),
                equipamentoRequestDTO.getAno(),equipamentoRequestDTO.getEmmanutencao(),
                equipamentoRequestDTO.getMarca(),equipamentoRequestDTO.getModelo()
        );
    }

    public EquipamentoResponseDTO toResponseDTO(Equipamento equipamento)throws Exception{
        return new EquipamentoResponseDTO(
                equipamento.getId(),clienteService.toResponseDTO(equipamento.getCliente()),
                equipamento.getModelo(),equipamento.getAno(),
                equipamento.getMarca(),equipamento.getEmmanutencao()
        );
    }

}
