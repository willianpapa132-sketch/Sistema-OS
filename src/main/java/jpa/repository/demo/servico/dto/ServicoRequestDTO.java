package jpa.repository.demo.servico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class ServicoRequestDTO {

    private Long id;

    @NotBlank(message = "Descricao e obrigatoria")
    private String descricao;

    @NotNull(message = "Valor e obrigatorio")
    @PositiveOrZero(message = "Valor nao pode ser negativo")
    private BigDecimal valor;

}
