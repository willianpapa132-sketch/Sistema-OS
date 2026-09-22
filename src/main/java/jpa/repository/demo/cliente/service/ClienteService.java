package jpa.repository.demo.cliente.service;

import jpa.repository.demo.cliente.entity.Cliente;
import jpa.repository.demo.cliente.dto.ClienteRequestDTO;
import jpa.repository.demo.cliente.dto.ClienteResponseDTO;
import jpa.repository.demo.cliente.repository.ClienteRepository;
import jpa.repository.demo.handler.BusinessException;
import jpa.repository.demo.handler.NotFoundException;
import jpa.repository.demo.ordemdeservico.repository.OrdemDeServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    OrdemDeServicoRepository ordemDeServicoRepository;

    public ClienteResponseDTO salvarCliente(ClienteRequestDTO clienteRequestDTO){
        Cliente cliente =  toEntity(clienteRequestDTO);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return toResponseDTO(clienteSalvo);
    }
    public ClienteResponseDTO mudarStatusCliente(ClienteRequestDTO clienteRequestDTO, Long id){

        Cliente clienteLocalizado = buscarClienteId(id);

        if(ordemDeServicoRepository.existsByCliente_id(clienteLocalizado.getId())){
            throw new BusinessException("cliente não pode ser desativado, com OS");
        }
        clienteLocalizado.setAtivo(clienteRequestDTO.isAtivo());
        Cliente clienteSalvo = clienteRepository.save(clienteLocalizado);
        return toResponseDTO(clienteSalvo);
    }

    public List<ClienteResponseDTO> listagemClientes() {

        List<Cliente> clientes = clienteRepository.findAll();

        return clientes.stream().map(cliente -> new ClienteResponseDTO(
                cliente.getId(), cliente.getNome(),
                cliente.getCpfcnpj(),cliente.getTelefone(),
                cliente.getEmail(),cliente.getAtivo()
        )).toList();
    }


    public Cliente buscarClienteId (Long id){
        return clienteRepository.findById(id).orElseThrow(()-> new NotFoundException("cliente não localizado"));
    }

    public ClienteResponseDTO buscarCliente(Long id){
        Cliente clienteLocalizado = buscarClienteId(id);
        return toResponseDTO(clienteLocalizado);
    }

    public Cliente toEntity(ClienteRequestDTO clienteRequestDTO){
        return new Cliente(
            clienteRequestDTO.getNome(),
            clienteRequestDTO.getTelefone(),clienteRequestDTO.getEmail(),
            clienteRequestDTO.isAtivo(),clienteRequestDTO.getCpfcnpj()
        );
    }

    public ClienteResponseDTO toResponseDTO(Cliente cliente){
        return new ClienteResponseDTO(
                cliente.getId(), cliente.getNome(),
                cliente.getCpfcnpj(), cliente.getTelefone(),
                cliente.getEmail(), cliente.getAtivo()
        );
    }



}
