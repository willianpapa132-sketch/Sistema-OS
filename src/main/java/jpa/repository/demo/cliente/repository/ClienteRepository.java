package jpa.repository.demo.cliente.repository;

import jpa.repository.demo.cliente.dto.ClienteJPQL;
import jpa.repository.demo.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(""" 
        SELECT cli.nome AS nome, cli.email AS email, cli.cpfcnpj AS cpfcnpj
        FROM Cliente cli
        """)
    List<ClienteJPQL> buscaDeDadosSimples ();
}
