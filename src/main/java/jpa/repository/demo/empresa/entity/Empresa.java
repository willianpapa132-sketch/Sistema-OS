package jpa.repository.demo.empresa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(length = 60)
    @NotBlank
    private String razaoSocial;

    @Column(length = 60)
    @NotBlank
    private String NomeFantasia;

    @Pattern(regexp = "\\d{14}")
    @NotBlank
    private String cnpj;

    @Size(min = 8, max = 15)
    private String inscricaoEstadual;

    @Size(min = 6, max = 16)
    private String inscricaoMunicipal;

    private Integer regimeTributario;

    @Embedded
    private Endereco endereco;

    @Pattern(regexp = "\\d{7}")
    private String cnae;






//códigos de serviço permitidos

  //  alíquota ISS

 //  retenção de ISS

    //certificado digital, quando exigido pelo provedor

   // credenciais/API tokens do emissor
}
