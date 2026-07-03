package jpa.repository.demo.domain.repository;
import jpa.repository.demo.domain.entity.OrdemDeServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface OrdemDeServicoRepository extends JpaRepository <OrdemDeServico, Long> {
}
