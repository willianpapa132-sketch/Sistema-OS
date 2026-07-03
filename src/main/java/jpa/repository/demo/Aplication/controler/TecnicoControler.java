package jpa.repository.demo.Aplication.controler;

import jpa.repository.demo.Aplication.service.TecnicoService;
import jpa.repository.demo.domain.dto.TecnicoRequestDTO;
import jpa.repository.demo.domain.dto.TecnicoResponseDTO;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tecnico")
public class TecnicoControler {

    private final TecnicoService tecnicoService;


    public TecnicoControler(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }


    @PostMapping
    public TecnicoResponseDTO cadastrarTecnico(@RequestBody TecnicoRequestDTO tecnicoRequestDTO) throws Exception {
        return tecnicoService.salvarTecnico(tecnicoRequestDTO);
    }
    @GetMapping
    public List<TecnicoResponseDTO> listarTecnico() throws Exception {
        return tecnicoService.listarTecnicos();
    }
    @GetMapping("/buscar/{id}")
    public TecnicoResponseDTO buscarTecnico(@PathVariable Long id) throws Exception {
        return tecnicoService.buscarTecnico(id);
    }
    @DeleteMapping("/deletar/{id}")
    public void deletarTecnico(@PathVariable Long id) throws Exception {
        tecnicoService.deletarTecnico(id);
    }
}
