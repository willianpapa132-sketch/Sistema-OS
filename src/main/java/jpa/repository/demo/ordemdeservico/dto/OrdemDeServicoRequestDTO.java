package jpa.repository.demo.ordemdeservico.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jpa.repository.demo.ordemdeservico.itemservico.dto.ItemServicoRequestDTO;
import jpa.repository.demo.ordemdeservico.entity.StatusOS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrdemDeServicoRequestDTO {

    private Long id;

    @NotNull(message = "Cliente e obrigatorio")
    private Long clienteid;

    @NotNull(message = "Tecnico e obrigatorio")
    private Long tecnicoid;

    @NotNull(message = "Equipamento e obrigatorio")
    private Long equipamentoid;


    private String defeitoRelatado;
    private String observacoes;

    @NotNull(message = "Status e obrigatorio")
    private StatusOS status;

    private BigDecimal valorPago;

    @Valid
    private List<ItemServicoRequestDTO> itens;
}
