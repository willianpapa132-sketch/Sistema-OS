package jpa.repository.demo.ordemdeservico.itemservico.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemServicoRequestDTO {

    private Long id;

    @NotNull(message = "Servico e obrigatorio")
    private Long servicoid;

    @PositiveOrZero(message = "Quantidade deve ser maior que zero")
    private int quantidade;



}
