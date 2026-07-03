package jpa.repository.demo.domain.repository;

import jpa.repository.demo.domain.entity.Equipamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long>{

}
