package jpa.repository.demo.ordemdeservico.repository;
import jpa.repository.demo.ordemdeservico.entity.OrdemDeServico;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrdemDeServicoRepository extends JpaRepository <OrdemDeServico, Long> {

    Boolean existsByCliente_id(Long id);
}
