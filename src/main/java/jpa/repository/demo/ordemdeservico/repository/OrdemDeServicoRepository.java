package jpa.repository.demo.ordemdeservico.repository;
import jpa.repository.demo.ordemdeservico.entity.OrdemDeServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface OrdemDeServicoRepository extends JpaRepository <OrdemDeServico, Long> {

}
