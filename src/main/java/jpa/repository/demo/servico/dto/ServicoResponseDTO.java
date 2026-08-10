package jpa.repository.demo.servico.dto;


import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class ServicoResponseDTO {

    private Long id;

    private String descricao;

    @PositiveOrZero
    private BigDecimal valor;

}
