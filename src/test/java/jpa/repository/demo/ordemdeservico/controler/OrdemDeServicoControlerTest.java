package jpa.repository.demo.ordemdeservico.controler;

import jpa.repository.demo.auth.authaplication.TokenService;
import jpa.repository.demo.auth.domain.UserRepository;
import jpa.repository.demo.cliente.dto.ClienteResponseDTO;
import jpa.repository.demo.equipamento.dto.EquipamentoResponseDTO;
import jpa.repository.demo.ordemdeservico.itemservico.dto.ItemServicoRequestDTO;
import jpa.repository.demo.ordemdeservico.dto.OrdemDeServicoRequestDTO;
import jpa.repository.demo.ordemdeservico.dto.OrdemDeServicoResponseDTO;
import jpa.repository.demo.ordemdeservico.entity.StatusOS;
import jpa.repository.demo.ordemdeservico.itemservico.dto.ItemServicoResponseDTO;
import jpa.repository.demo.ordemdeservico.service.OrdemDeServicoService;
import jpa.repository.demo.servico.dto.ServicoResponseDTO;
import jpa.repository.demo.tecnico.dto.TecnicoResponseDTO;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdemDeServicoControler.class)
@AutoConfigureMockMvc(addFilters = false)
class OrdemDeServicoControlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrdemDeServicoService  ordemDeServicoService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserRepository userRepository;

    //
    //
    //agora config para retornar as funcionalidades
    //
    //
    private OrdemDeServicoRequestDTO ordemDeServicoRequestDTO;

    private OrdemDeServicoResponseDTO  ordemDeServicoResponseDTO;

    private List<OrdemDeServicoResponseDTO> ordemDeServicoResponseDTOList;

    @BeforeEach
    void setup(){

        ordemDeServicoRequestDTO = new OrdemDeServicoRequestDTO();
        ordemDeServicoResponseDTO = new OrdemDeServicoResponseDTO();


        //////////////////////////////////////////////////////////////// request ->
        ItemServicoRequestDTO itemServicoRequestDTO1 = new ItemServicoRequestDTO();
        ItemServicoRequestDTO itemServicoRequestDTO2 = new ItemServicoRequestDTO();

        itemServicoRequestDTO1.setServicoid(1L);
        itemServicoRequestDTO1.setQuantidade(3);
        itemServicoRequestDTO2.setServicoid(2L);
        itemServicoRequestDTO2.setQuantidade(4);

        List<ItemServicoRequestDTO> itemServicoRequestDTOList = new ArrayList<>();

        itemServicoRequestDTOList.add(itemServicoRequestDTO1);
        itemServicoRequestDTOList.add(itemServicoRequestDTO2);

        ordemDeServicoRequestDTO.setTecnicoid(1L);
        ordemDeServicoRequestDTO.setClienteid(1L);
        ordemDeServicoRequestDTO.setEquipamentoid(1L);
        ordemDeServicoRequestDTO.setDefeitoRelatado("maçaneta da porta quebrada");
        ordemDeServicoRequestDTO.setObservacoes("deve ser cobrado a troca mais a peça do cliente");
        ordemDeServicoRequestDTO.setStatus(StatusOS.AGUARDANDO_APROVACAO);
        ordemDeServicoRequestDTO.setItens(itemServicoRequestDTOList);
        ordemDeServicoRequestDTO.setValorPago(new BigDecimal("00"));
        ////////////////////////////////////////////////////////////////////////////////////




        //////////////////////////////////////////////////////////// response ->
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(
                1L, "willian","11199004928", "41999265298",
                "willianpapa132@gmail.com", true
        );

        EquipamentoResponseDTO equipamentoResponseDTO = new EquipamentoResponseDTO(
                1L, clienteResponseDTO, "voyage", 2011, "vowsgagen", true
        );

        TecnicoResponseDTO tecnicoResponseDTO1 = new TecnicoResponseDTO(
                1L, "cleitin"
        );

        ServicoResponseDTO servicoResponseDTO1 = new ServicoResponseDTO(
                1L  , "Troca de maçaneta" , new BigDecimal("100.20")
        );
        ServicoResponseDTO servicoResponseDTO2 = new ServicoResponseDTO(
                2L  , "pintura" , new BigDecimal("90.20")
        );

        List<ItemServicoResponseDTO> itemServicoResponseDTOS = new ArrayList<>();

        ItemServicoResponseDTO itemServicoResponseDTO1 = new ItemServicoResponseDTO(
                1L, 1L, servicoResponseDTO1, 3, new BigDecimal("200.60")
        );
        ItemServicoResponseDTO itemServicoResponseDTO2 = new ItemServicoResponseDTO(
                2L, 1L, servicoResponseDTO2, 4, new BigDecimal("300.60")
        );

        itemServicoResponseDTOS.add(itemServicoResponseDTO1);
        itemServicoResponseDTOS.add(itemServicoResponseDTO2);
        /////////////////////////////////////////////////////////////////////

        // ordem response
        ordemDeServicoResponseDTO.setId(1L);
        ordemDeServicoResponseDTO.setStatus(StatusOS.APROVADA);
        ordemDeServicoResponseDTO.setCliente(clienteResponseDTO);
        ordemDeServicoResponseDTO.setEquipamento(equipamentoResponseDTO);
        ordemDeServicoResponseDTO.setTecnico(tecnicoResponseDTO1);
        ordemDeServicoResponseDTO.setObservacoes("deve ser cobrado a troca mais a peça do cliente");
        ordemDeServicoResponseDTO.setDataAbertura(LocalDateTime.now());
        ordemDeServicoResponseDTO.setDefeitoRelatado("maçaneta da porta quebrada");
        ordemDeServicoResponseDTO.setItens(itemServicoResponseDTOS);

    }
    ////////////////////////////////////// post ->
    @Test
    @DisplayName("padrã de salvamento, deve retornar 200")
    void cadastrarOrdem() throws Exception {


        when(ordemDeServicoService.salvarOrdem(any(OrdemDeServicoRequestDTO.class))).thenReturn(ordemDeServicoResponseDTO);

        mockMvc.perform(
                post("/ordemdeservico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordemDeServicoRequestDTO))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("APROVADA"))
                .andExpect(jsonPath("$.cliente.id").value(1L))
                .andExpect(jsonPath("$.cliente.nome").value("willian"))
                .andExpect(jsonPath("$.cliente.cpfcnpj").value("11199004928"))
                .andExpect(jsonPath("$.cliente.telefone").value("41999265298"))
                .andExpect(jsonPath("$.cliente.email").value("willianpapa132@gmail.com"))
                .andExpect(jsonPath("$.cliente.ativo").value(true))
                .andExpect(jsonPath("$.dataFinalizacao").isEmpty())
                .andExpect(jsonPath("$.observacoes").value("deve ser cobrado a troca mais a peça do cliente"));

        verify(ordemDeServicoService).salvarOrdem(any(OrdemDeServicoRequestDTO.class));
    }

    @Test
    @DisplayName("vai lança excessão no valid de criação ")
    void deveQuebrarNoValid() throws Exception {

        ordemDeServicoRequestDTO.setClienteid(null);

        mockMvc.perform(
                post("/ordemdeservico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordemDeServicoRequestDTO))
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("clienteid: Cliente e obrigatorio"));

        verifyNoInteractions(ordemDeServicoService);
    }

    @Test
    @DisplayName("vai lança excessão no valid de criação nos itens")
    void deveQuebrarNoValidDoItem() throws Exception {

        for (ItemServicoRequestDTO itemServicoRequestDTO : ordemDeServicoRequestDTO.getItens()){
            itemServicoRequestDTO.setQuantidade(-1);
            break;
        }

        mockMvc.perform(
                        post("/ordemdeservico")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ordemDeServicoRequestDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem")
                        .value("itens[0].quantidade: Quantidade deve ser maior que zero"));

        verifyNoInteractions(ordemDeServicoService);
    }
    ///////////////////////////////////////////////////////////


    ////////////////////////////// put ->
    @Test
    @DisplayName("atualizar e retornar o status como 200")
    void atualizarOrdem() throws Exception {


        when(ordemDeServicoService.atualizarOrdem(any(OrdemDeServicoRequestDTO.class), eq(1L))).thenReturn(ordemDeServicoResponseDTO);


        mockMvc.perform(
                put("/ordemdeservico/atualizar/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordemDeServicoRequestDTO))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cliente.nome").value("willian"));
    }


    ///////////////////////////////////////
    @Test
    void fecharOrdem() {
    }

    @Test
    void cancelarOrdem() {
    }

    @Test
    void buscarOrdem() {
    }

    @Test
    void listarOrdem() {
    }
}