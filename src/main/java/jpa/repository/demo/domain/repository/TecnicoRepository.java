package jpa.repository.demo.domain.repository;

import jpa.repository.demo.domain.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TecnicoRepository extends JpaRepository<Tecnico,Long> {
}
