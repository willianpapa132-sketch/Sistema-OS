package jpa.repository.demo.cliente.repository;

import jpa.repository.demo.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
