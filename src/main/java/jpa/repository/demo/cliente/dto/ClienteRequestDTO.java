package jpa.repository.demo.cliente.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ClienteRequestDTO {

    private Long id;

    @NotBlank(message = "Nome e obrigatorio")
    private String nome;

    @Email(message = "Email invalido")
    private String email;

    @NotBlank(message = "CPF/CNPJ e obrigatorio")
    @Pattern(regexp = "\\d{11}|\\d{14}", message = "CPF/CNPJ deve ter 11 ou 14 digitos")
    private String cpfcnpj;

    private String telefone;
    private boolean ativo;
}
