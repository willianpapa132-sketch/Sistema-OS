package jpa.repository.demo.cliente.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jpa.repository.demo.equipamento.entity.Equipamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    @NotBlank
    @Size(min = 3 , max = 20, message = "nome invalido")
    private String nome;

    @Column(nullable = false)
    @Pattern(regexp = "\\d{11}|\\d{14}")
    private  String cpfcnpj;

    @Size(min = 10, max =11, message = "quantidade numerica invalida")
    private String telefone;

    @Email
    private String email;

    private Boolean ativo;

    @OneToMany(mappedBy = "cliente")
    private List<Equipamento> equipamentos;

    public Cliente( String nome,String telefone, String email, Boolean ativo, String cpfcnpj) {
        this.telefone = telefone;
        this.nome = nome;
        this.email = email;
        this.ativo = ativo;
        this.cpfcnpj = cpfcnpj;
    }
}
