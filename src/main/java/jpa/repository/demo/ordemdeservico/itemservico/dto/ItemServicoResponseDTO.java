package jpa.repository.demo.ordemdeservico.itemservico.dto;


import jakarta.validation.constraints.NotNull;
import jpa.repository.demo.servico.dto.ServicoResponseDTO;
import jpa.repository.demo.servico.entity.Servico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class ItemServicoResponseDTO {
    @NotNull
    private Long id;

    private Long ordemDeServicoid;

    private ServicoResponseDTO servico;

    private int quantidade;

    private BigDecimal valortot;

}
