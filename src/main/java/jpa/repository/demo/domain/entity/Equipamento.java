package jpa.repository.demo.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipamento_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private String modelo;

    private int ano;

    @Column(nullable = false)
    private String marca;

    private Boolean emmanutencao;

    public Equipamento(Cliente cliente,int ano, Boolean emmanutencao, String marca, String modelo) {
        this.cliente = cliente;
        this.ano = ano;
        this.emmanutencao = emmanutencao;
        this.marca = marca;
        this.modelo = modelo;
    }

}
