package jpa.repository.demo.Aplication.service;

import jpa.repository.demo.domain.dto.ClienteRequestDTO;
import jpa.repository.demo.domain.dto.ClienteResponseDTO;
import jpa.repository.demo.domain.entity.Cliente;
import jpa.repository.demo.domain.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public ClienteResponseDTO salvarCliente(ClienteRequestDTO clienteRequestDTO){
        Cliente cliente =  toEntity(clienteRequestDTO);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return toResponseDTO(clienteSalvo);
    }
    public ClienteResponseDTO mudarStatusCliente(ClienteRequestDTO clienteRequestDTO, Long id)throws Exception{
        Cliente clienteLocalizado = buscarClienteId(id);
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


    public Cliente buscarClienteId (Long id)throws Exception{
        return clienteRepository.findById(id).orElseThrow(()-> new Exception("cliente não localizado"));
    }

    public ClienteResponseDTO buscarCliente(Long id)throws Exception{
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
