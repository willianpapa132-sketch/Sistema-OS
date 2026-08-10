package jpa.repository.demo.itemservico.repository;

import jpa.repository.demo.itemservico.entity.ItemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ItemServicoRepository extends JpaRepository<ItemServico, Long> {
    long countByServico_Id(Long servicoId);
}
