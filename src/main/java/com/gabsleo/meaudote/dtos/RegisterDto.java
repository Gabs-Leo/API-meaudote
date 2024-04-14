package com.gabsleo.meaudote.dtos;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record RegisterDto(
        @CPF(message = "Invalid CPF.") String cpf,
        @NotEmpty(message = "Username cant be empty.") String name,
        @NotEmpty(message = "Password cant be empty.") String password,
        @Email(message = "Invalid email.") String email,
        @NotEmpty(message = "Phone cant be empty.") String phone,
        Boolean isNGO,
        @NotEmpty(message = "State cant be empty.") String state,
        @NotEmpty(message = "City cant be empty.") String city
) {}
