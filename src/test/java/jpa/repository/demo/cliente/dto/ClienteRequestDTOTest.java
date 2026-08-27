package jpa.repository.demo.cliente.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClienteRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        validator = factory.getValidator();
    }

    @Test
    void deveDarErroQuandoCpfForInvalido() {

        ClienteRequestDTO dto = new ClienteRequestDTO(
                "willian",
                "willian@gmail.com",
                "123", // inválido
                "41999265298",
                true
        );

        Set<ConstraintViolation<ClienteRequestDTO>> erros =
                validator.validate(dto);

        assertFalse(erros.isEmpty());
    }
}