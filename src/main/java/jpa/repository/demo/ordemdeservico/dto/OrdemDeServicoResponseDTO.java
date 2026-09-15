package jpa.repository.demo.ordemdeservico.dto;

import jpa.repository.demo.cliente.dto.ClienteResponseDTO;
import jpa.repository.demo.equipamento.dto.EquipamentoResponseDTO;
import jpa.repository.demo.ordemdeservico.itemservico.dto.ItemServicoResponseDTO;
import jpa.repository.demo.tecnico.dto.TecnicoResponseDTO;
import jpa.repository.demo.ordemdeservico.entity.StatusOS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    private String defeitoRelatado;
    private String observacoes;
    private StatusOS status;
    private BigDecimal valorTotalOrdem;
    private BigDecimal valorPago;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFinalizacao;
    private List<ItemServicoResponseDTO> itens;
}
