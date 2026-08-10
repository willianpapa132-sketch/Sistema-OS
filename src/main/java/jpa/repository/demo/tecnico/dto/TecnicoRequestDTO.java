package jpa.repository.demo.tecnico.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TecnicoRequestDTO {
    private Long id;

    @NotBlank(message = "Nome e obrigatorio")
    private String nome;

}
