package jpa.repository.demo.equipamento.service;

import jpa.repository.demo.cliente.service.ClienteService;
import jpa.repository.demo.equipamento.dto.EquipamentoRequestDTO;
import jpa.repository.demo.equipamento.dto.EquipamentoResponseDTO;
import jpa.repository.demo.equipamento.entity.Equipamento;
import jpa.repository.demo.equipamento.repository.EquipamentoRepository;
import jpa.repository.demo.handler.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipamentoService {

    @Autowired
    EquipamentoRepository equipamentoRepository;

    @Autowired
    ClienteService clienteService;


    public EquipamentoResponseDTO salvarEquipamento(EquipamentoRequestDTO equipamentoRequestDTO)  {
        Equipamento equipamento = toEntity(equipamentoRequestDTO);
        Equipamento equipamentoSalvo = equipamentoRepository.save(equipamento);
        return toResponseDTO(equipamentoSalvo);
    }

    public EquipamentoResponseDTO  alterarStatus(EquipamentoResponseDTO equipamentoResponseDTO) {
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

    public String deletarEquipamento(Long id){
        Equipamento equipamentoLocalizo = equipamentoRepository.findById(id).orElseThrow(()-> new NotFoundException("não foi localizado esse equipamento"));
        equipamentoRepository.delete(equipamentoLocalizo);
        return "equipamento deletado com sucesso";
    }

    public Equipamento buscarEquipamentoId (Long id) {
        return equipamentoRepository.findById(id).orElseThrow(()-> new NotFoundException("não foi localizado esse equipamento"));
    }

    public EquipamentoResponseDTO buscarEquipamento (Long id) {
        Equipamento equipamentoLocalizado = buscarEquipamentoId(id);
        return toResponseDTO(equipamentoLocalizado);
    }

    public Equipamento toEntity(EquipamentoRequestDTO equipamentoRequestDTO) {
        return new Equipamento(
                clienteService.buscarClienteId(equipamentoRequestDTO.getClienteId()),
                equipamentoRequestDTO.getAno(),equipamentoRequestDTO.getEmmanutencao(),
                equipamentoRequestDTO.getMarca(),equipamentoRequestDTO.getModelo()
        );
    }

    public EquipamentoResponseDTO toResponseDTO(Equipamento equipamento){
        return new EquipamentoResponseDTO(
                equipamento.getId(),clienteService.toResponseDTO(equipamento.getCliente()),
                equipamento.getModelo(),equipamento.getAno(),
                equipamento.getMarca(),equipamento.getEmmanutencao()
        );
    }

}
