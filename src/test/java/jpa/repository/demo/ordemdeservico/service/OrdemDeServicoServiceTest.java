package jpa.repository.demo.ordemdeservico.service;

import jpa.repository.demo.cliente.entity.Cliente;
import jpa.repository.demo.cliente.service.ClienteService;
import jpa.repository.demo.equipamento.entity.Equipamento;
import jpa.repository.demo.equipamento.repository.EquipamentoRepository;
import jpa.repository.demo.equipamento.service.EquipamentoService;
import jpa.repository.demo.handler.BusinessException;
import jpa.repository.demo.itemservico.dto.ItemServicoRequestDTO;
import jpa.repository.demo.itemservico.entity.ItemServico;
import jpa.repository.demo.itemservico.service.ItemServicoService;
import jpa.repository.demo.ordemdeservico.dto.OrdemDeServicoRequestDTO;
import jpa.repository.demo.ordemdeservico.dto.OrdemDeServicoResponseDTO;
import jpa.repository.demo.ordemdeservico.entity.OrdemDeServico;
import jpa.repository.demo.ordemdeservico.entity.StatusOS;
import jpa.repository.demo.ordemdeservico.repository.OrdemDeServicoRepository;
import jpa.repository.demo.servico.entity.Servico;
import jpa.repository.demo.tecnico.entity.Tecnico;
import jpa.repository.demo.tecnico.service.TecnicoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdemDeServicoServiceTest {

    @Mock
    OrdemDeServicoRepository ordemDeServicoRepository;

    @Mock
    EquipamentoRepository equipamentoRepository;

    @Mock
    private ItemServicoService itemServicoService;

    @Mock
    private ClienteService clienteService;

    @Mock
    private EquipamentoService equipamentoService;

    @Mock
    private TecnicoService tecnicoService;

    @InjectMocks
    private OrdemDeServicoService ordemDeServicoService;




    private OrdemDeServicoRequestDTO criarOrdem(){
        OrdemDeServicoRequestDTO ordemDeServicoRequestDTO = new OrdemDeServicoRequestDTO();
        ordemDeServicoRequestDTO.setClienteid(1L);
        ordemDeServicoRequestDTO.setEquipamentoid(1L);
        ordemDeServicoRequestDTO.setTecnicoid(1L);
        ordemDeServicoRequestDTO.setDefeitoRelatado("estrago a bobina do motor causando fundimento");
        ordemDeServicoRequestDTO.setObservacoes("serviço levera cerca de 15 dias");
        ordemDeServicoRequestDTO.setStatus(StatusOS.AGUARDANDO_APROVACAO);
        return ordemDeServicoRequestDTO;
        // depois set do list itemrequest
    }
    private List<ItemServicoRequestDTO> criarLista(){
        List<ItemServicoRequestDTO> lista = new ArrayList<>();
        ItemServicoRequestDTO itemServicoRequestDTO1 = new ItemServicoRequestDTO();
        ItemServicoRequestDTO itemServicoRequestDTO2 = new ItemServicoRequestDTO();
        itemServicoRequestDTO1.setServicoid(1L);
        itemServicoRequestDTO1.setQuantidade(2);
        itemServicoRequestDTO2.setServicoid(2L);
        itemServicoRequestDTO2.setQuantidade(1);
        lista.add(itemServicoRequestDTO1);
        lista.add(itemServicoRequestDTO2);
        return lista;
        //criar 2 serviços com o id 1L E 2L
    }
    private Equipamento criarEquipamento(){
        Equipamento equipamento1= new Equipamento();
        equipamento1.setId(1L);
        equipamento1.setAno(2011);
        equipamento1.setMarca("Volksvagen");
        equipamento1.setModelo("Voyage");
        equipamento1.setEmmanutencao(false);
        return equipamento1;
        //vai falta fazer o set cliente
    }
    private Servico criarServico1 (){
        Servico servico1= new Servico();
        servico1.setId(1L);
        servico1.setDescricao("refinamento motor");
        servico1.setValor(new BigDecimal("2000.70"));
        return servico1;
        
    }
    private Servico criarServico2 (){
        Servico servico1= new Servico();
        servico1.setId(2L);
        servico1.setDescricao("mangueira arrefecimento ");
        servico1.setValor(new BigDecimal("100.70"));
        return servico1;

    }
    private Cliente  criarCliente(){
        Cliente cliente1= new Cliente();
        cliente1.setId(1L);
        cliente1.setNome("willian");
        cliente1.setTelefone("411988882");
        cliente1.setCpfcnpj("11199004928");
        cliente1.setAtivo(true);
        cliente1.setEmail("willian@gmail.com");
        return cliente1;
    }
    private Tecnico criarTecnico(){
        Tecnico tecnico1= new Tecnico();
        tecnico1.setId(1L);
        tecnico1.setNome("Alberto");
        return tecnico1;
    }


    @Test
    @DisplayName("teste padrão de uma criação de OS sem quebra e sem lançamento de Excessão")
    void deveSalvarOrdem() {

        //criação das entity
        Cliente cliente = criarCliente();
        Equipamento equipamento = criarEquipamento();
        Tecnico tecnico = criarTecnico();
        Servico servico1 = criarServico1();
        Servico servico2 = criarServico2();

        //implementando a Ordem de serviço com os dados
        List<ItemServicoRequestDTO> itemServicoRequestDTOS = criarLista();
        OrdemDeServicoRequestDTO ordemDeServicoRequestDTO = criarOrdem();
        ordemDeServicoRequestDTO.setItens(itemServicoRequestDTOS);

        when(clienteService.buscarClienteId(1L)).thenReturn(cliente);
        when(equipamentoService.buscarEquipamentoId(1L)).thenReturn(equipamento);
        when(tecnicoService.buscarTecnicoId(1L)).thenReturn(tecnico);
        //equipamento vai fica como false, não estara em manutenção



        //aqui foi feito a conversão dos itens
        ItemServicoRequestDTO itemServicoRequestDTO1 = itemServicoRequestDTOS.get(0);
        ItemServicoRequestDTO itemServicoRequestDTO2 = itemServicoRequestDTOS.get(1);

        ItemServico itemServico1 = new ItemServico();
        itemServico1.setId(1L);
        itemServico1.setServico(servico1);
        itemServico1.setQuantidade(itemServicoRequestDTO1.getQuantidade());

        ItemServico itemServico2= new ItemServico();
        itemServico2.setId(2L);
        itemServico2.setServico(servico2);
        itemServico2.setQuantidade(itemServicoRequestDTO2.getQuantidade());

        when(itemServicoService.toEntity(itemServicoRequestDTO1)).thenReturn(itemServico1);
        when(itemServicoService.toEntity(itemServicoRequestDTO2)).thenReturn(itemServico2);

        //ultima config de retorno
        when(ordemDeServicoRepository.save(any(OrdemDeServico.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));



        //metodo sendo executado e retornando para o dto sem cliente tecnico e equipamento,não foi mocado o toResponse:
        OrdemDeServicoResponseDTO resultado =
                ordemDeServicoService.salvarOrdem(ordemDeServicoRequestDTO);



        ArgumentCaptor<OrdemDeServico> captor =
                ArgumentCaptor.forClass(OrdemDeServico.class);


        verify(clienteService).buscarClienteId(1L);
        verify(equipamentoService).buscarEquipamentoId(1L);
        verify(tecnicoService).buscarTecnicoId(1L);
        verify(itemServicoService).toEntity(itemServicoRequestDTO1);
        verify(itemServicoService).toEntity(itemServicoRequestDTO2);
        verify(ordemDeServicoRepository).save(any(OrdemDeServico.class));
        verify(ordemDeServicoRepository).save(captor.capture());



        OrdemDeServico ordemSalva = captor.getValue();

        assertFalse(ordemSalva.getEquipamento().getEmmanutencao());

        assertEquals(
                0,
                new BigDecimal("4102.10")
                        .compareTo(resultado.getValorTotalOrdem())
        );
        assertEquals("willian",ordemSalva.getCliente().getNome());
        verify(equipamentoRepository,never()).save(any(Equipamento.class));
    }
    @Test
    void deveQuebrarNoValidation(){
        Cliente cliente = criarCliente();
        Equipamento equipamento = criarEquipamento();
        Tecnico tecnico = criarTecnico();
        Servico servico1 = criarServico1();
        Servico servico2 = criarServico2();

        List<ItemServicoRequestDTO> itemServicoRequestDTOS = criarLista();
        OrdemDeServicoRequestDTO ordemDeServicoRequestDTO = criarOrdem();
        ordemDeServicoRequestDTO.setItens(itemServicoRequestDTOS);
        ordemDeServicoRequestDTO.setStatus(StatusOS.EM_ANDAMENTO);

        ItemServicoRequestDTO itemServicoRequestDTO1 = itemServicoRequestDTOS.get(0);
        ItemServicoRequestDTO itemServicoRequestDTO2 = itemServicoRequestDTOS.get(1);

        ItemServico itemServico1 = new ItemServico();
        itemServico1.setId(1L);
        itemServico1.setServico(servico1);
        itemServico1.setQuantidade(itemServicoRequestDTO1.getQuantidade());

        ItemServico itemServico2= new ItemServico();
        itemServico2.setId(2L);
        itemServico2.setServico(servico2);
        itemServico2.setQuantidade(itemServicoRequestDTO2.getQuantidade());




        when(clienteService.buscarClienteId(1L)).thenReturn(null);

        verify(ordemDeServicoRepository, never())
                .save(any(OrdemDeServico.class));
        verify(equipamentoRepository, never()).save(any(Equipamento.class));
        BusinessException exception=
                assertThrows(BusinessException.class,() -> ordemDeServicoService.salvarOrdem(ordemDeServicoRequestDTO));

        assertEquals("nao pode ser feito uma ordem de servico sem CLIENTE informado", exception.getMessage());


    }
}