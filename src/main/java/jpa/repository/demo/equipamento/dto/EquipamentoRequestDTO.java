package jpa.repository.demo.equipamento.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EquipamentoRequestDTO {

    @NotNull(message = "Cliente e obrigatorio")
    private Long clienteId;

    @NotBlank(message = "Modelo e obrigatorio")
    private String modelo;

    @Min(value = 1900, message = "Ano deve ser maior ou igual a 1900")
    private int ano;

    @NotBlank(message = "Marca e obrigatoria")
    private String marca;

    private Boolean emmanutencao;
}
