package jpa.repository.demo.domain.dto;

import jpa.repository.demo.domain.entity.ItemServico;
import jpa.repository.demo.domain.entity.StatusOS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrdemDeServicoRequestDTO {
    private Long id;
    private Long clienteid;
    private Long tecnicoid;
    private Long equipamentoid;
    private Long servicoid;
    private List<ItemServico> itemServicos;
    private String defeitoRelatado;
    private String observacoes;
    private StatusOS status;
    private double valorPago;
    private List<ItemServicoRequestDTO> itens;
}
