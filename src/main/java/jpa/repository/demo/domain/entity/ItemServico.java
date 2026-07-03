package jpa.repository.demo.domain.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
    private int quantidade;

    private double valortot;


    public ItemServico(OrdemDeServico ordemDeServico, Servico servico, int quantidade, double valortot) {
        this.ordemDeServico = ordemDeServico;
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

    public double getValortot() {
        return valortot;
    }

    public void setValortot(double valortot) {
        this.valortot = valortot;
    }
}
