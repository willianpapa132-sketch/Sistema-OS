package jpa.repository.demo.tecnico.controler;

import jakarta.validation.Valid;
import jpa.repository.demo.tecnico.service.TecnicoService;
import jpa.repository.demo.tecnico.dto.TecnicoRequestDTO;
import jpa.repository.demo.tecnico.dto.TecnicoResponseDTO;
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
    public TecnicoResponseDTO cadastrarTecnico(@RequestBody @Valid TecnicoRequestDTO tecnicoRequestDTO) throws Exception {
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
