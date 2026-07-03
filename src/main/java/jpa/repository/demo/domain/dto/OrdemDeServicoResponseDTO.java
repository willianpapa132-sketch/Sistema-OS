package jpa.repository.demo.domain.dto;

import jpa.repository.demo.domain.entity.StatusOS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrdemDeServicoResponseDTO {
    private Long id;
    private ClienteResponseDTO cliente;
    private TecnicoResponseDTO tecnico;
    private EquipamentoResponseDTO equipamento;
    private ServicoResponseDTO servico;
    private String defeitoRelatado;
    private String observacoes;
    private StatusOS status;
    private double valorTotalOrdem;
    private double valorPago;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFinalizacao;
    private List<ItemServicoResponseDTO> itens;
}
