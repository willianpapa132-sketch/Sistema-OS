package jpa.repository.demo.tecnico.repository;

import jpa.repository.demo.tecnico.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TecnicoRepository extends JpaRepository<Tecnico,Long> {
}
