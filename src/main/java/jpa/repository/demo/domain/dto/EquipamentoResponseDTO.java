package jpa.repository.demo.domain.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class EquipamentoResponseDTO {
    @NotNull
    private Long id;

    @NotNull
    private ClienteResponseDTO clienteId;

    private String modelo;

    private int ano;

    private String marca;

    private Boolean emmanutencao;
}
