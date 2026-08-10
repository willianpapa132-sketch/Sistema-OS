package jpa.repository.demo.servico.repository;

import jpa.repository.demo.servico.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ServicoRepository extends JpaRepository<Servico,Long> {
}
