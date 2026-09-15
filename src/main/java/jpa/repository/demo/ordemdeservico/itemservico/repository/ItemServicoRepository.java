package jpa.repository.demo.ordemdeservico.itemservico.repository;

import jpa.repository.demo.ordemdeservico.itemservico.entity.ItemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ItemServicoRepository extends JpaRepository<ItemServico, Long> {
    long countByServico_Id(Long servicoId);
}
