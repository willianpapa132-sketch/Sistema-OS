package jpa.repository.demo.cliente.controler;

import jakarta.validation.Valid;
import jpa.repository.demo.cliente.dto.ClienteRequestDTO;
import jpa.repository.demo.cliente.dto.ClienteResponseDTO;
import jpa.repository.demo.cliente.service.ClienteService;
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
    public ClienteResponseDTO cadastrar(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        return clienteService.salvarCliente(clienteRequestDTO);
    }

    @PutMapping("/atualizar/{id}")
    public ClienteResponseDTO mudarStatus(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO, @PathVariable Long id) throws Exception {
        return clienteService.mudarStatusCliente(clienteRequestDTO, id);
    }

    @GetMapping
    public List<ClienteResponseDTO> listarClientes() {
        return clienteService.listagemClientes();
    }

    @GetMapping("/buscar/{id}")
    public ClienteResponseDTO buscarcliente(@PathVariable Long id)   {
        return clienteService.buscarCliente(id);
    }

}
