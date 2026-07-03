package jpa.repository.demo.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cliente {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private  String cpfcnpj;

    private String telefone;

    @Email
    private String email;

    private Boolean ativo;

    public Cliente( String nome,String telefone, String email, Boolean ativo, String cpfcnpj) {
        this.telefone = telefone;
        this.nome = nome;
        this.email = email;
        this.ativo = ativo;
        this.cpfcnpj = cpfcnpj;
    }
}
