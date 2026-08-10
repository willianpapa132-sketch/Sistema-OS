package jpa.repository.demo.servico.controler;

import jakarta.validation.Valid;
import jpa.repository.demo.servico.service.ServicoService;
import jpa.repository.demo.servico.dto.ServicoRequestDTO;
import jpa.repository.demo.servico.dto.ServicoResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servico")
public class ServicoControler {

    private final ServicoService servicoService;

    public ServicoControler(ServicoService servicoService) {
        this.servicoService = servicoService;
    }



    @PostMapping
    public ServicoResponseDTO cadastrarServico(@RequestBody @Valid ServicoRequestDTO servicoRequestDTO)   {
        return servicoService.salvarServico(servicoRequestDTO);
    }

    @GetMapping("/buscar/{id}")
    public ServicoResponseDTO buscarServico(@PathVariable Long id)   {
        return servicoService.buscarServico(id);
    }

    @GetMapping
    public List<ServicoResponseDTO> listaDeServicos()   {
        return servicoService.listarServicos();
    }
    @PutMapping("/editar/{id}")
    public ServicoResponseDTO editarServico(@RequestBody @Valid ServicoRequestDTO servicoRequestDTO,@PathVariable Long id)   {
        return servicoService.editarServico(servicoRequestDTO, id);
    }
    @DeleteMapping("/deletar/{id}")
    public String excluirServico(@PathVariable Long id){
        return servicoService.deletarServico(id);
    }

}
