package jpa.repository.demo.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ServicoResponseDTO {

    private Long id;

    private String descricao;

    private Double valor;

}
