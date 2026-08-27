package jpa.repository.demo.ordemdeservico.controler;

import jakarta.validation.Valid;
import jpa.repository.demo.ordemdeservico.service.OrdemDeServicoService;
import jpa.repository.demo.ordemdeservico.dto.OrdemDeServicoRequestDTO;
import jpa.repository.demo.ordemdeservico.dto.OrdemDeServicoResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordemdeservico")
public class OrdemDeServicoControler {

    private final OrdemDeServicoService ordemService;

    OrdemDeServicoControler(OrdemDeServicoService ordemDeServicoService) {
        this.ordemService = ordemDeServicoService;
    }

    @PostMapping
    public OrdemDeServicoResponseDTO cadastrarOrdem(@RequestBody @Valid OrdemDeServicoRequestDTO ordemDeServicoRequestDTO)   {
        return ordemService.salvarOrdem(ordemDeServicoRequestDTO);
    }
    @PutMapping("/atualizar/{id}")
    public OrdemDeServicoResponseDTO atualizarOrdem(@RequestBody @Valid OrdemDeServicoRequestDTO ordemDeServicoRequestDTO,@PathVariable Long id)   {
        return  ordemService.atualizarOrdem(ordemDeServicoRequestDTO,id);
    }
    @PutMapping("/fechar/{id}")
    public OrdemDeServicoResponseDTO fecharOrdem(@PathVariable Long id)   {
        return ordemService.fecharOrdem(id);
    }
    @PutMapping("/cancelar/{id}")
    public OrdemDeServicoResponseDTO cancelarOrdem (@PathVariable Long id)   {
        return ordemService.cancelarOrdem(id);
    }
    @GetMapping("/buscar/{id}")
    public OrdemDeServicoResponseDTO buscarOrdem(@PathVariable Long id)  {
        return ordemService.buscarOrdem(id);
    }
    @GetMapping
    public List<OrdemDeServicoResponseDTO> listarOrdem()   {
        return ordemService.listarTodasOrdem();
    }

}
