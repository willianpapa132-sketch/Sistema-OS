package jpa.repository.demo.Aplication.service;

import jpa.repository.demo.domain.dto.ItemServicoRequestDTO;
import jpa.repository.demo.domain.dto.ItemServicoResponseDTO;
import jpa.repository.demo.domain.dto.OrdemDeServicoRequestDTO;
import jpa.repository.demo.domain.dto.OrdemDeServicoResponseDTO;
import jpa.repository.demo.domain.entity.ItemServico;
import jpa.repository.demo.domain.entity.OrdemDeServico;
import jpa.repository.demo.domain.entity.StatusOS;
import jpa.repository.demo.domain.repository.EquipamentoRepository;
import jpa.repository.demo.domain.repository.OrdemDeServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ServicoService servicoService;

    public OrdemDeServicoResponseDTO salvarOrdem(OrdemDeServicoRequestDTO ordemDeServicoRequestDTO) throws Exception {
        OrdemDeServico ordemDeServico = toEntity(ordemDeServicoRequestDTO);

        if (ordemDeServico.getCliente() == null) {
            throw new Exception("nao pode ser feito uma ordem de servico sem CLIENTE informado");
        }
        if (ordemDeServico.getEquipamento() == null) {
            throw new Exception("nao pode ser feito uma ordem de servico sem EQUIPAMENTO informado");
        }
        if (ordemDeServico.getTecnico() == null) {
            throw new Exception("nao pode ser feito uma ordem de servico sem TECNICO informado");
        }
        if (ordemDeServico.getCliente().getAtivo() == false) {
            throw new Exception("Esse cliente esta DESATIVADO");
        }
        if (ordemDeServico.getStatus() == StatusOS.EM_ANDAMENTO || ordemDeServico.getStatus() == StatusOS.AGUARDANDO_PECA) {
            ordemDeServico.getEquipamento().setEmmanutencao(true);
            equipamentoRepository.save(ordemDeServico.getEquipamento());
        }

        ordemDeServico.setDataAbertura(LocalDateTime.now());
        ordemDeServico.setDataFinalizacao(null);
        ordemDeServico.setValorTotalOrdem(0);
        ordemDeServico.setItens(null);
        OrdemDeServico ordemSalva = ordemDeServicoRepository.save(ordemDeServico);

        double total = 0;
        List<ItemServico> itensSalvos = new ArrayList<>();

        if (ordemDeServicoRequestDTO.getItens() != null) {
            for (ItemServicoRequestDTO itemDTO : ordemDeServicoRequestDTO.getItens()) {
                ItemServico item = itemServicoService.toEntity(itemDTO, ordemSalva);
                ItemServico itemSalvo = itemServicoService.salvarItem(item);

                itensSalvos.add(itemSalvo);
                total += itemSalvo.getValortot();
            }
        }

        ordemSalva.setValorTotalOrdem(total);
        ordemSalva.setItens(itensSalvos);
        return toResponseDTO(ordemDeServicoRepository.save(ordemSalva));
    }

    public OrdemDeServicoResponseDTO atualizarOrdem(OrdemDeServicoRequestDTO ordemDeServicoRequestDTO, Long id) throws Exception {
        OrdemDeServico ordemDeServicoLocalizada = buscarPorID(id);
        if (ordemDeServicoLocalizada == null) {
            throw new Exception("Ordem não localizada");
        }
        ordemDeServicoLocalizada = toEntity(ordemDeServicoRequestDTO);
        ordemDeServicoLocalizada.setId(id);
        if (ordemDeServicoLocalizada.getStatus() == StatusOS.EM_ANDAMENTO || ordemDeServicoLocalizada.getStatus() == StatusOS.AGUARDANDO_PECA) {
            ordemDeServicoLocalizada.getEquipamento().setEmmanutencao(true);
            equipamentoRepository.save(ordemDeServicoLocalizada.getEquipamento());
        }
        if (ordemDeServicoLocalizada.getStatus() == StatusOS.FINALIZADA || ordemDeServicoLocalizada.getStatus() == StatusOS.FATURADA || ordemDeServicoLocalizada.getStatus() == StatusOS.ENTREGUE || ordemDeServicoLocalizada.getStatus() == StatusOS.CANCELADA) {
            ordemDeServicoLocalizada.getEquipamento().setEmmanutencao(false);
            equipamentoRepository.save(ordemDeServicoLocalizada.getEquipamento());
        }
        if (ordemDeServicoLocalizada.getStatus() == StatusOS.FINALIZADA && ordemDeServicoLocalizada.getDataFinalizacao() == null) {
            ordemDeServicoLocalizada.setDataFinalizacao(LocalDateTime.now());
        }
        if (ordemDeServicoLocalizada.getStatus() != StatusOS.FINALIZADA) {
            ordemDeServicoLocalizada.setDataFinalizacao(null);
        }

        OrdemDeServico ordemSalva = ordemDeServicoRepository.save(ordemDeServicoLocalizada);
        double total = 0;
        List<ItemServico> itensSalvos = new ArrayList<>();

        if (ordemDeServicoRequestDTO.getItens() != null) {
            for (ItemServicoRequestDTO itemDTO : ordemDeServicoRequestDTO.getItens()) {
                ItemServico item = itemServicoService.toEntity(itemDTO, ordemSalva);
                ItemServico itemSalvo = itemServicoService.salvarItem(item);

                itensSalvos.add(itemSalvo);
                total += itemSalvo.getValortot();
            }
        }

        ordemSalva.setValorTotalOrdem(total);
        ordemSalva.setItens(itensSalvos);
        return toResponseDTO(ordemDeServicoRepository.save(ordemSalva));

    }

    public OrdemDeServicoResponseDTO fecharOrdem(OrdemDeServicoRequestDTO ordemDeServicoRequestDTO, Long id) throws Exception {
        OrdemDeServico ordemDeServico = toEntity(ordemDeServicoRequestDTO);
        ordemDeServico.setId(id);
        if (ordemDeServico.getStatus() == StatusOS.FINALIZADA || ordemDeServico.getStatus() == StatusOS.FATURADA || ordemDeServico.getStatus() == StatusOS.ENTREGUE || ordemDeServico.getStatus() == StatusOS.CANCELADA) {
            ordemDeServico.getEquipamento().setEmmanutencao(false);
            equipamentoRepository.save(ordemDeServico.getEquipamento());
        }
        if (ordemDeServico.getStatus() == StatusOS.EM_ANDAMENTO || ordemDeServico.getStatus() == StatusOS.AGUARDANDO_PECA) {
            throw new Exception("O Status da ordem de servico deve ser finalizada");
        }
        if (ordemDeServico.getValorTotalOrdem() > ordemDeServico.getValorPago()) {
            throw new Exception("valores de pagamento com diferimento");
        }

        ordemDeServico.setDataFinalizacao(LocalDateTime.now());
        return toResponseDTO(ordemDeServicoRepository.save(ordemDeServico));
    }

    public List<OrdemDeServicoResponseDTO> listarTodasOrdem() throws Exception {
        List<OrdemDeServicoResponseDTO> ordens = new ArrayList<>();
        for (OrdemDeServico ordemDeServico : ordemDeServicoRepository.findAll()) {
            ordens.add(toResponseDTO(ordemDeServico));
        }
        return ordens;
    }

    public OrdemDeServico buscarPorID(Long id) throws Exception {
        return ordemDeServicoRepository.findById(id)
                .orElseThrow(() -> new Exception("nao foi localizado essa ordem de servico"));
    }
    public OrdemDeServicoResponseDTO buscarOrdem(Long id) throws Exception {
        OrdemDeServico ordemDeServico = ordemDeServicoRepository.findById(id)
                .orElseThrow(() -> new Exception("nao foi localizado essa ordem de servico"));
        return toResponseDTO(ordemDeServico);
    }

    public OrdemDeServicoResponseDTO cancelarOrdem(Long id) throws Exception {
        OrdemDeServico ordemLocalizada = ordemDeServicoRepository.findById(id)
                .orElseThrow(() -> new Exception("nao foi localizado essa ordem de servico"));

        if (ordemLocalizada.getStatus() == StatusOS.FINALIZADA || ordemLocalizada.getStatus() == StatusOS.FATURADA || ordemLocalizada.getStatus() == StatusOS.ENTREGUE) {
            throw new Exception("Nao e permitido cancelar uma ordem de servico com Status de finalizada, faturada ou entregue");
        }

        ordemLocalizada.setStatus(StatusOS.CANCELADA);
        ordemLocalizada.getEquipamento().setEmmanutencao(false);
        equipamentoRepository.save(ordemLocalizada.getEquipamento());

        return toResponseDTO(ordemDeServicoRepository.save(ordemLocalizada));
    }

    private OrdemDeServico toEntity(OrdemDeServicoRequestDTO dto) throws Exception {
        OrdemDeServico ordemDeServico = new OrdemDeServico();

        ordemDeServico.setId(dto.getId());
        ordemDeServico.setCliente(clienteService.buscarClienteId(dto.getClienteid()));
        ordemDeServico.setTecnico(tecnicoService.buscarTecnicoId(dto.getTecnicoid()));
        ordemDeServico.setEquipamento(equipamentoService.buscarEquipamentoId(dto.getEquipamentoid()));
        ordemDeServico.setServico(servicoService.buscarServicoId(dto.getServicoid()));
        ordemDeServico.setDefeitoRelatado(dto.getDefeitoRelatado());
        ordemDeServico.setObservacoes(dto.getObservacoes());
        ordemDeServico.setStatus(dto.getStatus());
        ordemDeServico.setValorTotalOrdem(0);
        ordemDeServico.setValorPago(dto.getValorPago());
        return ordemDeServico;
    }

    private OrdemDeServicoResponseDTO toResponseDTO(OrdemDeServico ordemDeServico) throws Exception {
        return new OrdemDeServicoResponseDTO(
                ordemDeServico.getId(),
                clienteService.toResponseDTO(ordemDeServico.getCliente()),
                tecnicoService.toResponseDTO(ordemDeServico.getTecnico()),
                equipamentoService.toResponseDTO(ordemDeServico.getEquipamento()),
                servicoService.toResponseDTO(ordemDeServico.getServico()),
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

    private List<ItemServicoResponseDTO> itensToResponse(List<ItemServico> itens) throws Exception {
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
