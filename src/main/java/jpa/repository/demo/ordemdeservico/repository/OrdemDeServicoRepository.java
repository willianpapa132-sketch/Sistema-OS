package jpa.repository.demo.ordemdeservico.repository;
import jpa.repository.demo.ordemdeservico.entity.OrdemDeServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


public interface OrdemDeServicoRepository extends JpaRepository <OrdemDeServico, Long> {
    List<OrdemDeServico> findByCliente_Id(Long id);
    Boolean filterByCliente_Id(Long id);
}
