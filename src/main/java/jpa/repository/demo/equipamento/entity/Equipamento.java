package jpa.repository.demo.equipamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jpa.repository.demo.cliente.entity.Cliente;
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
    @Column(name = "equipamento_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, length = 20)
    @NotBlank
    @Size(min = 1, max = 20)
    private String modelo;

    @Positive
    private int ano;

    @Column(nullable = false, length = 20)
    @Size(min = 1, max = 20)
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
