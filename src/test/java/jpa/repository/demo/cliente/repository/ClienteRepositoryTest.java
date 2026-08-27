package jpa.repository.demo.cliente.repository;
import jakarta.persistence.EntityManager;
import jpa.repository.demo.cliente.dto.ClienteJPQL;
import jpa.repository.demo.cliente.dto.ClienteRequestDTO;
import jpa.repository.demo.cliente.entity.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ClienteRepositoryTest {

    @Autowired
    EntityManager em ;


    @Autowired
    ClienteRepository clienteRepository;

    @Test
    @DisplayName("teste de JPQL")
    void deveBuscarDadosSimplesDosClientes() {
        ClienteRequestDTO clienteRequestDTO1 = new ClienteRequestDTO(
                "willian", "willian@gmail.com", "11199004928","41999265298", true
        );
        ClienteRequestDTO clienteRequestDTO2 = new ClienteRequestDTO(
                "gustavo", "gustavo@gmail.com", "99999999991","41999265299", true
        );

        this.createCliente(clienteRequestDTO1);
        this.createCliente(clienteRequestDTO2);

        List<ClienteJPQL> resultado = this.clienteRepository.buscaDeDadosSimples();

        assertEquals(2, resultado.size());

    }
    @Test
    @DisplayName("teste de JPQL com dados vazios")
    void deveBuscarDadosSimplesDosClientesVazio() {


        List<ClienteJPQL> resultado = this.clienteRepository.buscaDeDadosSimples();

        assertEquals(0, resultado.size());

    }
    private Cliente createCliente(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteRequestDTO.getNome());
        cliente.setEmail(clienteRequestDTO.getEmail());
        cliente.setCpfcnpj(clienteRequestDTO.getCpfcnpj());
        cliente.setTelefone(clienteRequestDTO.getTelefone());
        cliente.setAtivo(clienteRequestDTO.isAtivo());
        this.em.persist(cliente);
        return cliente;

    }
}