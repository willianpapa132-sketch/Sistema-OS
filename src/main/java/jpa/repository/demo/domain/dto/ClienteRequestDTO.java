package jpa.repository.demo.domain.dto;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ClienteRequestDTO {

    private Long id;
    private String nome;
    @Email
    private String email;
    private String cpfcnpj;
    private String telefone;
    private boolean ativo;
}
