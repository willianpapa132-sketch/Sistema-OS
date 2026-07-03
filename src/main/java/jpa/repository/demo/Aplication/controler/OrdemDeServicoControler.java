package jpa.repository.demo.Aplication.controler;

import jpa.repository.demo.Aplication.service.OrdemDeServicoService;
import jpa.repository.demo.domain.dto.OrdemDeServicoRequestDTO;
import jpa.repository.demo.domain.dto.OrdemDeServicoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
    public OrdemDeServicoResponseDTO cadastrarOrdem(@RequestBody OrdemDeServicoRequestDTO ordemDeServicoRequestDTO) throws Exception {
        return ordemService.salvarOrdem(ordemDeServicoRequestDTO);
    }
    @PutMapping("/atualizar/{id}")
    public OrdemDeServicoResponseDTO atualizarOrdem(@RequestBody OrdemDeServicoRequestDTO ordemDeServicoRequestDTO,@PathVariable Long id) throws Exception {
        return  ordemService.atualizarOrdem(ordemDeServicoRequestDTO,id);
    }
    @PutMapping("/fechar/{id}")
    public OrdemDeServicoResponseDTO fecharOrdem(OrdemDeServicoRequestDTO ordemDeServicoRequestDTO ,@PathVariable Long id) throws Exception {
        return ordemService.fecharOrdem(ordemDeServicoRequestDTO ,id);
    }
    @PutMapping("/cancelar/{id}")
    public OrdemDeServicoResponseDTO cancelarOrdem (@PathVariable Long id) throws Exception {
        return ordemService.cancelarOrdem(id);
    }
    @GetMapping("/buscar/{id}")
    public OrdemDeServicoResponseDTO buscarOrdem(@RequestParam Long id) throws Exception {
        return ordemService.buscarOrdem(id);
    }
    @GetMapping
    public List<OrdemDeServicoResponseDTO> listarOrdem() throws Exception {
        return ordemService.listarTodasOrdem();
    }

}
