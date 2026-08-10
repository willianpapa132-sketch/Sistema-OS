package jpa.repository.demo.equipamento.controler;

import jakarta.validation.Valid;
import jpa.repository.demo.equipamento.service.EquipamentoService;
import jpa.repository.demo.equipamento.dto.EquipamentoRequestDTO;
import jpa.repository.demo.equipamento.dto.EquipamentoResponseDTO;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/equipamento")
public class EquipamentoControler {


    private final EquipamentoService equipamentoService;
    public EquipamentoControler(EquipamentoService equipamentoService){
        this.equipamentoService = equipamentoService;
    }

    @PostMapping
    public EquipamentoResponseDTO cadastrarEquipamento(@RequestBody @Valid EquipamentoRequestDTO equipamentoRequestDTO) throws Exception {
        return equipamentoService.salvarEquipamento(equipamentoRequestDTO);
    }
    @PutMapping
    public EquipamentoResponseDTO mudarStatusEquipamento(@RequestBody EquipamentoResponseDTO equipamentoResponseDTO) throws Exception{
        return equipamentoService.alterarStatus(equipamentoResponseDTO);
    }
    @GetMapping
    public List<EquipamentoResponseDTO> listarEquipamentos(){
        return equipamentoService.listarEquipamentos();
    }
    @GetMapping("/buscar/{id}")
    public EquipamentoResponseDTO buscarEquipamento(@PathVariable Long id)throws Exception{
        return equipamentoService.buscarEquipamento(id);
    }
    @DeleteMapping("/deletar/{id}")
    public String deletarEquipamento(@PathVariable Long id)throws Exception{
        return equipamentoService.deletarEquipamento(id);
    }

}
