package jpa.repository.demo.domain.dto;


import jakarta.validation.constraints.NotNull;
import jpa.repository.demo.domain.entity.Servico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ItemServicoResponseDTO {
    @NotNull
    private Long id;

    private Long ordemDeServicoid;

    private Servico servico;

    private int quantidade;

    private double valortot;

}
