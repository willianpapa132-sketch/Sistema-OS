package jpa.repository.demo.cliente.controler;

import jpa.repository.demo.auth.authaplication.TokenService;
import jpa.repository.demo.auth.domain.UserRepository;
import jpa.repository.demo.cliente.dto.ClienteRequestDTO;
import jpa.repository.demo.cliente.dto.ClienteResponseDTO;
import jpa.repository.demo.cliente.service.ClienteService;
import jpa.repository.demo.handler.BusinessException;
import jpa.repository.demo.handler.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteControler.class)
@AutoConfigureMockMvc(addFilters = false)
class ClienteControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    TokenService tokenService;

    @MockitoBean
    private UserRepository userRepository;




    private ClienteRequestDTO clienteRequestDTO;
    private  ClienteResponseDTO clienteResponseDTO;

    @BeforeEach
    void setUp() {
        clienteResponseDTO = new ClienteResponseDTO(
                1L, "Willian", "11199004928", "41999265298","willianpapa132@gmail.com", true
        );
        clienteRequestDTO = new ClienteRequestDTO(
                "Willian",
                "willianpapa132@gmail.com",
                "11199004928",
                "41999265298",
                true
        );
    }





    //Testes do metodo Post

    @Test
    @DisplayName("aplicação correta de como deve ser feito")
    void deveCadastrareRetornarResponseDTO() throws Exception {



        when(clienteService.salvarCliente(any(ClienteRequestDTO.class))).thenReturn(clienteResponseDTO);

        mockMvc.perform(
                        post("/cliente")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(clienteRequestDTO))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Willian"))
                .andExpect(jsonPath("$.cpfcnpj").value("11199004928"))
                .andExpect(jsonPath("$.telefone").value("41999265298"))
                .andExpect(jsonPath("$.email").value("willianpapa132@gmail.com"))
                .andExpect(jsonPath("$.ativo").value(true));

        verify(clienteService).salvarCliente(any(ClienteRequestDTO.class));
    }

    @Test
    @DisplayName("vai quebrar com o valid")
    public void deveQuebrarNoValid()throws Exception {

        clienteRequestDTO.setNome("Willian");
        clienteRequestDTO.setCpfcnpj("11199004928");
        clienteRequestDTO.setTelefone("41999265298");
        clienteRequestDTO.setEmail("email-invalido");


        mockMvc.perform(
                post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteRequestDTO))
        ).andExpect(status().isBadRequest());


        verify(clienteService, never()).salvarCliente(any(ClienteRequestDTO.class));
    }


    //
    //
    //
    //
    //
    //testes do put

    @Test
    @DisplayName("atualização deve acontecer ok e retornar status 200")
    public void deveAtualizarStatusRetornar200() throws Exception {


        when(clienteService.mudarStatusCliente(any(ClienteRequestDTO.class), eq(1L))).thenReturn(clienteResponseDTO);

        mockMvc.perform(
                put("/cliente/atualizar/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteRequestDTO))
        )
                .andExpect(status().isOk());

        verify(clienteService).mudarStatusCliente(any(ClienteRequestDTO.class), eq(1L));
    }



    @Test
    @DisplayName("deve lançar o Bussines exception no service")
    public void deveBussinesExceptionNoService()throws Exception {

        when(clienteService.mudarStatusCliente(any(ClienteRequestDTO.class), eq(1L)))
                .thenThrow(new BusinessException("cliente tem OS em seu nome"));

        mockMvc.perform(
                put("/cliente/atualizar/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteRequestDTO))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("cliente tem OS em seu nome"));

        verify(clienteService).mudarStatusCliente(any(ClienteRequestDTO.class), eq(1L));
        assertTrue(clienteRequestDTO.isAtivo());
    }

    //
    //
    //
    //
    //
    //
    //metodos get

    @Test
    @DisplayName("deve funcionar normalmente e status 200")
    public void deveFuncionarNormalmenteeStatus200()throws Exception {

        when(clienteService.buscarCliente(1L)).thenReturn(clienteResponseDTO);

        mockMvc.perform(
                get("/cliente/buscar/{id}",1L)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Willian"))
                .andExpect(jsonPath("$.cpfcnpj").value("11199004928"));

        verify(clienteService).buscarCliente(1L);
    }


    @Test
    @DisplayName("deve lançar uma excessão notfound de cliente não achado")
    public void deveNaoLocalizarOCliente() throws Exception {

        when(clienteService.buscarCliente(1L)).thenThrow(new NotFoundException("cliente não encontrado"));


        //esse voce não precisa aplica um json e nem converte objeto porque a propia url recebe o valor
        mockMvc.perform(
                get("/cliente/buscar/{id}",1L)
        )
        .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem")
                        .value("cliente não encontrado"));

        verify(clienteService, times(1)).buscarCliente(1L);

    }
}
