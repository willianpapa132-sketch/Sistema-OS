package jpa.repository.demo.Aplication.controler;

import jpa.repository.demo.Aplication.service.ServicoService;
import jpa.repository.demo.domain.dto.ServicoRequestDTO;
import jpa.repository.demo.domain.dto.ServicoResponseDTO;
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
    public ServicoResponseDTO cadastrarServico(@RequestBody ServicoRequestDTO servicoRequestDTO) throws Exception {
        return servicoService.salvarServico(servicoRequestDTO);
    }

    @GetMapping("/buscar/{id}")
    public ServicoResponseDTO buscarServico(@PathVariable Long id) throws Exception {
        return servicoService.buscarServico(id);
    }

    @GetMapping
    public List<ServicoResponseDTO> listaDeServicos() throws Exception {
        return servicoService.listarServicos();
    }
    @PutMapping("/editar/{id}")
    public ServicoResponseDTO editarServico(@RequestBody ServicoRequestDTO servicoRequestDTO,@PathVariable Long id) throws Exception {
        return servicoService.editarServico(servicoRequestDTO, id);
    }
    @DeleteMapping("/deletar/{id}")
    public void excluirServico(@PathVariable Long id) throws Exception {
        servicoService.deletarServico(id);
    }

}
