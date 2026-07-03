package jpa.repository.demo.Aplication.controler;

import jpa.repository.demo.Aplication.service.ClienteService;
import jpa.repository.demo.domain.dto.ClienteRequestDTO;
import jpa.repository.demo.domain.dto.ClienteResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteControler {

    private final ClienteService clienteService;

    public ClienteControler(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @PostMapping
    public ClienteResponseDTO cadastrar(@RequestBody ClienteRequestDTO clienteRequestDTO) {
        return clienteService.salvarCliente(clienteRequestDTO);
    }

    @PutMapping("/atualizar/{id}")
    public ClienteResponseDTO mudarStatus(@RequestBody ClienteRequestDTO clienteRequestDTO, @PathVariable Long id) throws Exception {
        return clienteService.mudarStatusCliente(clienteRequestDTO, id);
    }

    @GetMapping
    public List<ClienteResponseDTO> listarClientes() {
        return clienteService.listagemClientes();
    }

    @GetMapping("/buscar/{id}")
    public ClienteResponseDTO buscarcliente(@PathVariable Long id) throws Exception {
        return clienteService.buscarCliente(id);
    }

}
