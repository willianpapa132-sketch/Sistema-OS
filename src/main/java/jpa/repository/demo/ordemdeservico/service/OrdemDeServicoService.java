package jpa.repository.demo.ordemdeservico.service;

import jakarta.transaction.Transactional;
import jpa.repository.demo.cliente.service.ClienteService;
import jpa.repository.demo.equipamento.service.EquipamentoService;
import jpa.repository.demo.ordemdeservico.itemservico.service.ItemServicoService;
import jpa.repository.demo.tecnico.service.TecnicoService;
import jpa.repository.demo.ordemdeservico.itemservico.dto.ItemServicoRequestDTO;
import jpa.repository.demo.ordemdeservico.itemservico.dto.ItemServicoResponseDTO;
import jpa.repository.demo.ordemdeservico.dto.OrdemDeServicoRequestDTO;
import jpa.repository.demo.ordemdeservico.dto.OrdemDeServicoResponseDTO;
import jpa.repository.demo.ordemdeservico.itemservico.entity.ItemServico;
import jpa.repository.demo.ordemdeservico.entity.OrdemDeServico;
import jpa.repository.demo.ordemdeservico.entity.StatusOS;
import jpa.repository.demo.equipamento.repository.EquipamentoRepository;
import jpa.repository.demo.ordemdeservico.repository.OrdemDeServicoRepository;
import jpa.repository.demo.handler.BusinessException;
import jpa.repository.demo.handler.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrdemDeServicoService {

    @Autowired
    OrdemDeServicoRepository ordemDeServicoRepository;

    @Autowired
    EquipamentoRepository equipamentoRepository;

    @Autowired
    private ItemServicoService itemServicoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EquipamentoService equipamentoService;

    @Autowired
    private TecnicoService tecnicoService;



    private void validation(OrdemDeServico ordemDeServico){
        if (ordemDeServico.getCliente() == null) {
            throw new BusinessException("nao pode ser feito uma ordem de servico sem CLIENTE informado");
        }
        if (ordemDeServico.getEquipamento() == null) {
            throw new BusinessException("nao pode ser feito uma ordem de servico sem EQUIPAMENTO informado");
        }
        if (ordemDeServico.getTecnico() == null) {
            throw new BusinessException("nao pode ser feito uma ordem de servico sem TECNICO informado");
        }
        if (ordemDeServico.getCliente().getAtivo() == false) {
            throw new BusinessException("Esse cliente esta DESATIVADO");
        }

    }




    @Transactional
    public OrdemDeServicoResponseDTO salvarOrdem(OrdemDeServicoRequestDTO ordemDeServicoRequestDTO) {
        OrdemDeServico ordemDeServico = toEntity(ordemDeServicoRequestDTO);
        validation(ordemDeServico);

        if (ordemDeServico.getStatus() == StatusOS.EM_ANDAMENTO || ordemDeServico.getStatus() == StatusOS.AGUARDANDO_PECA) {
            ordemDeServico.getEquipamento().setEmmanutencao(true);
            equipamentoRepository.save(ordemDeServico.getEquipamento());
        }

        ordemDeServico.setDataAbertura(LocalDateTime.now());
        ordemDeServico.setDataFinalizacao(null);
        ordemDeServico.setValorTotalOrdem(BigDecimal.valueOf(0));
        BigDecimal total = BigDecimal.valueOf(0);

        if (ordemDeServicoRequestDTO.getItens() != null) {
            for (ItemServicoRequestDTO itemDTO : ordemDeServicoRequestDTO.getItens()) {
                ItemServico item = itemServicoService.toEntity(itemDTO);
                item.setValortot( item.getServico().getValor().multiply(BigDecimal.valueOf( item.getQuantidade())));
                ordemDeServico.adicionarItem(item);
                total = total.add(item.getValortot());
            }
        }
        ordemDeServico.setValorTotalOrdem(total);
        return toResponseDTO(ordemDeServicoRepository.save(ordemDeServico));
    }



    @Transactional
    public OrdemDeServicoResponseDTO atualizarOrdem(OrdemDeServicoRequestDTO ordemDeServicoRequestDTO, Long id)  {

        OrdemDeServico orderDeServicoLocalizada = buscarPorID(id);
        orderDeServicoLocalizada.limparItens();


        validation(orderDeServicoLocalizada);

        BigDecimal total =BigDecimal.valueOf(0);
        if (ordemDeServicoRequestDTO.getItens() != null) {
            for (ItemServicoRequestDTO itemDTO : ordemDeServicoRequestDTO.getItens()) {
                ItemServico item = itemServicoService.toEntity(itemDTO);
                item.setValortot( item.getServico().getValor().multiply(BigDecimal.valueOf( item.getQuantidade())));
                orderDeServicoLocalizada.adicionarItem(item);
                total = total.add( item.getValortot());
            }
        }
        orderDeServicoLocalizada.setValorTotalOrdem(total);
        return toResponseDTO(orderDeServicoLocalizada);

    }

    @Transactional
    public OrdemDeServicoResponseDTO fecharOrdem( Long id)   {
        OrdemDeServico ordemDeServicoLocalizada = buscarPorID(id);
        validation(ordemDeServicoLocalizada);
        ordemDeServicoLocalizada.setStatus(StatusOS.FINALIZADA);
        ordemDeServicoLocalizada.getEquipamento().setEmmanutencao(false);
        equipamentoRepository.save(ordemDeServicoLocalizada.getEquipamento());

        if (ordemDeServicoLocalizada.getValorTotalOrdem().compareTo( ordemDeServicoLocalizada.getValorPago()) >0){
            throw new BusinessException("valores de pagamento com diferimento");
        }

        ordemDeServicoLocalizada.setDataFinalizacao(LocalDateTime.now());
        return  toResponseDTO (ordemDeServicoLocalizada);
    }

    public List<OrdemDeServicoResponseDTO> listarTodasOrdem()   {
        List<OrdemDeServicoResponseDTO> ordens = new ArrayList<>();
        for (OrdemDeServico ordemDeServico : ordemDeServicoRepository.findAll()) {
            ordens.add(toResponseDTO(ordemDeServico));
        }
        return ordens;
    }

    public OrdemDeServico buscarPorID(Long id)   {
        return ordemDeServicoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("nao foi localizado essa ordem de servico"));
    }
    public OrdemDeServicoResponseDTO buscarOrdem(Long id)  {
        OrdemDeServico ordemDeServico = ordemDeServicoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("nao foi localizado essa ordem de servico"));
        return toResponseDTO(ordemDeServico);
    }

    public OrdemDeServicoResponseDTO cancelarOrdem(Long id)   {
        OrdemDeServico ordemLocalizada = buscarPorID(id);

        if (ordemLocalizada.getStatus() == StatusOS.FINALIZADA || ordemLocalizada.getStatus() == StatusOS.FATURADA || ordemLocalizada.getStatus() == StatusOS.ENTREGUE) {
            throw new BusinessException("Nao e permitido cancelar uma ordem de servico com Status de finalizada, faturada ou entregue");
        }

        ordemLocalizada.setStatus(StatusOS.CANCELADA);
        ordemLocalizada.getEquipamento().setEmmanutencao(false);
        equipamentoRepository.save(ordemLocalizada.getEquipamento());

        return toResponseDTO(ordemDeServicoRepository.save(ordemLocalizada));
    }

    private void deletarOS(Long id)  {
        ordemDeServicoRepository.deleteById(id);
    }

    private OrdemDeServico toEntity(OrdemDeServicoRequestDTO dto)  {
        OrdemDeServico ordemDeServico = new OrdemDeServico();

        ordemDeServico.setId(dto.getId());
        ordemDeServico.setCliente(clienteService.buscarClienteId(dto.getClienteid()));
        ordemDeServico.setTecnico(tecnicoService.buscarTecnicoId(dto.getTecnicoid()));
        ordemDeServico.setEquipamento(equipamentoService.buscarEquipamentoId(dto.getEquipamentoid()));
        ordemDeServico.setDefeitoRelatado(dto.getDefeitoRelatado());
        ordemDeServico.setObservacoes(dto.getObservacoes());
        ordemDeServico.setStatus(dto.getStatus());
        ordemDeServico.setValorTotalOrdem(BigDecimal.valueOf(0));
        ordemDeServico.setValorPago(dto.getValorPago());
        return ordemDeServico;
    }

    private OrdemDeServicoResponseDTO toResponseDTO(OrdemDeServico ordemDeServico) {
        return new OrdemDeServicoResponseDTO(
                ordemDeServico.getId(),
                clienteService.toResponseDTO(ordemDeServico.getCliente()),
                tecnicoService.toResponseDTO(ordemDeServico.getTecnico()),
                equipamentoService.toResponseDTO(ordemDeServico.getEquipamento()),
                ordemDeServico.getDefeitoRelatado(),
                ordemDeServico.getObservacoes(),
                ordemDeServico.getStatus(),
                ordemDeServico.getValorTotalOrdem(),
                ordemDeServico.getValorPago(),
                ordemDeServico.getDataAbertura(),
                ordemDeServico.getDataFinalizacao(),
                itensToResponse(ordemDeServico.getItens())
        );
    }

    private List<ItemServicoResponseDTO> itensToResponse(List<ItemServico> itens)  {
        if (itens == null) {
            return null;
        }

        List<ItemServicoResponseDTO> itensResponse = new ArrayList<>();
        for (ItemServico item : itens) {
            itensResponse.add(itemServicoService.toResponse(item));
        }
        return itensResponse;
    }
}
