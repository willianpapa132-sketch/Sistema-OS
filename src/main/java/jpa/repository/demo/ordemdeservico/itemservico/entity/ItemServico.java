package jpa.repository.demo.ordemdeservico.itemservico.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import jpa.repository.demo.ordemdeservico.entity.OrdemDeServico;
import jpa.repository.demo.servico.entity.Servico;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class ItemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "itemservico_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordem_de_servico_id")
    private OrdemDeServico ordemDeServico;

    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @Column(nullable = false)
    @PositiveOrZero
    @Max(100000)
    private int quantidade;

    @PositiveOrZero
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valortot;


    public ItemServico( Servico servico, int quantidade, BigDecimal valortot) {

        this.quantidade = quantidade;
        this.valortot = valortot;
        this.servico = servico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdemDeServico getOrdemDeServico() {
        return ordemDeServico;
    }

    public void setOrdemDeServico(OrdemDeServico ordemDeServico) {
        this.ordemDeServico = ordemDeServico;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValortot() {
        return valortot;
    }

    public void setValortot(BigDecimal valortot) {
        this.valortot = valortot;
    }
}
