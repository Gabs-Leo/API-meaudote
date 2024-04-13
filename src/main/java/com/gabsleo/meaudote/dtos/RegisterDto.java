package com.gabsleo.meaudote.dtos;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;

public record RegisterDto(
        @CPF(message = "Invalid CPF.") String cpf,
        String name,
        String password,
        @Email(message = "Invalid email.") String email,
        String phone,
        Boolean isNGO,
        String state,
        String city
) {}
