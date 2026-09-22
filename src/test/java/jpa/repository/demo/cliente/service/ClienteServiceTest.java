package jpa.repository.demo.cliente.service;

import jpa.repository.demo.cliente.dto.ClienteRequestDTO;
import jpa.repository.demo.cliente.dto.ClienteResponseDTO;
import jpa.repository.demo.cliente.entity.Cliente;
import jpa.repository.demo.cliente.repository.ClienteRepository;
import jpa.repository.demo.handler.NotFoundException;
import jpa.repository.demo.ordemdeservico.repository.OrdemDeServicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {


    @Mock
    OrdemDeServicoRepository ordemDeServicoRepository;
    
    @Mock
    ClienteRepository clienteRepository;

    @InjectMocks
    ClienteService clienteService;

    //Metodos para praparar os testes

    private ClienteRequestDTO criarRequestCliente() {
        return new ClienteRequestDTO(
                "willian",
                "willian@gmail.com",
                "11199004928",
                "41999265298",
                true
        );
    }
    public Cliente toEntity(ClienteRequestDTO clienteRequestDTO){
        return new Cliente(
                clienteRequestDTO.getNome(),
                clienteRequestDTO.getTelefone(),clienteRequestDTO.getEmail(),
                clienteRequestDTO.isAtivo(),clienteRequestDTO.getCpfcnpj()
        );
    }



    @Test
    @DisplayName("teste de primeiro cadastro do cliente")
    void salvarCliente() {
            ClienteRequestDTO requestDTO = new ClienteRequestDTO(
                    "willian", "willian.c9digital@gmail.com",
                    "11199004928", "41999265298" , true
            );

            Cliente clienteSalvo = toEntity(requestDTO);
            clienteSalvo.setId(1L);

            when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSalvo);


            ClienteResponseDTO testeRetorno =  clienteService.salvarCliente(requestDTO);

            assertNotNull(testeRetorno);
            assertEquals("willian",testeRetorno.getNome());
            assertEquals("willian.c9digital@gmail.com",testeRetorno.getEmail());
             verify(clienteRepository, times(1))
                .save(any(Cliente.class));


    }
    @Test
    @DisplayName("testando a alteração dos status")
    void deveMudarStatus(){
        ClienteRequestDTO requestDTO = criarRequestCliente();
        requestDTO.setAtivo(false);
        Cliente clienteSalvo = toEntity(requestDTO);
        clienteSalvo.setId(1L);


        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteSalvo));
        when(clienteRepository.save(any(Cliente.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(ordemDeServicoRepository.existsByCliente_id(1L)).thenReturn(false);

        ClienteResponseDTO clienteResponseDTO = clienteService.mudarStatusCliente(requestDTO, 1L);
        assertNotNull(clienteResponseDTO);
        assertEquals("willian",clienteResponseDTO.getNome());
        assertFalse(clienteResponseDTO.getAtivo());

        verify(this.clienteRepository, times(1)).findById(1L);
        verify(this.clienteRepository, times(1)).save(any(Cliente.class));

    }

    @Test
    void lancaNotFoundException(){

        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());


        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> clienteService.buscarClienteId(99L)
        );

        verify(clienteRepository, times(1)).findById(99L);
    }






}