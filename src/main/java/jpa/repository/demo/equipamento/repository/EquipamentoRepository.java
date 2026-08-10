package jpa.repository.demo.equipamento.repository;

import jpa.repository.demo.equipamento.entity.Equipamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long>{

}
