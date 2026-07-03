package jpa.repository.demo.domain.dto;
import jakarta.persistence.ManyToOne;
import jpa.repository.demo.domain.entity.OrdemDeServico;
import jpa.repository.demo.domain.entity.Servico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ItemServicoRequestDTO {

    @ManyToOne
    private Long ordemDeServicoID;

    @ManyToOne
    private Servico servico;

    private int quantidade;



}
