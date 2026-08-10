package jpa.repository.demo.cliente.dto;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ClienteResponseDTO {

    private Long id;

    private String nome;

    private  String cpfcnpj;

    private String telefone;

    @Email
    private String email;

    private Boolean ativo;

}
