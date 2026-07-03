package jpa.repository.demo.domain.repository;

import jpa.repository.demo.domain.entity.ItemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ItemServicoRepository extends JpaRepository<ItemServico, Long> {
}
