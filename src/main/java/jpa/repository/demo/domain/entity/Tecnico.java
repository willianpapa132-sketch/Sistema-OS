package jpa.repository.demo.domain.entity;

import jakarta.persistence.*;

@Entity
@Table
public class Tecnico {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "tecnico_id")
    private Long id;

    @Column(nullable = false)
    private String nome;

    public Tecnico() {
    }

    public Tecnico(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
