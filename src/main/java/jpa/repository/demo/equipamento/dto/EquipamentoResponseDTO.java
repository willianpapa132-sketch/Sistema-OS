package jpa.repository.demo.equipamento.dto;


import jakarta.validation.constraints.NotNull;
import jpa.repository.demo.cliente.dto.ClienteResponseDTO;
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
