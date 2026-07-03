package jpa.repository.demo.domain.repository;

import jpa.repository.demo.domain.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ServicoRepository extends JpaRepository<Servico,Long> {
}
