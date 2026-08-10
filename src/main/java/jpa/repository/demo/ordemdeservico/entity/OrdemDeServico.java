package jpa.repository.demo.ordemdeservico.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import jpa.repository.demo.cliente.entity.Cliente;
import jpa.repository.demo.equipamento.entity.Equipamento;
import jpa.repository.demo.itemservico.entity.ItemServico;
import jpa.repository.demo.tecnico.entity.Tecnico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdemDeServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "os_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "tecnico_id", nullable = false)
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @Column(columnDefinition = "TEXT", length = 100)
    @Size(min = 5 ,max = 100)
    private String defeitoRelatado;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Enumerated(EnumType.STRING)
    private StatusOS status;

    @PositiveOrZero
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTotalOrdem;

    @PositiveOrZero
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorPago;

    private LocalDateTime dataAbertura;

    private LocalDateTime dataFinalizacao;

    @OneToMany(
            mappedBy = "ordemDeServico" ,
            cascade = CascadeType.ALL ,
            orphanRemoval = true
    )
    private List<ItemServico> itens = new ArrayList<>();



    public void adicionarItem(ItemServico item) {
        itens.add(item);
        item.setOrdemDeServico(this);
    }

    public void removerItem(ItemServico item) {
        itens.remove(item);
        item.setOrdemDeServico(null);
    }

    public void limparItens() {
        for(ItemServico itemServico : itens) {
            itemServico.setOrdemDeServico(null);
        }
        itens.clear();
    }

}
