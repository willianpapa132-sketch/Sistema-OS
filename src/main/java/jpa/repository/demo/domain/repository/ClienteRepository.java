package jpa.repository.demo.domain.repository;

import jpa.repository.demo.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
