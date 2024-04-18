package com.gabsleo.meaudote.dtos.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;
import java.util.Date;
import java.util.List;

public record AdminAppUserDto(
        @CPF(message = "Invalid CPF.") String cpf,
        @NotEmpty(message = "Username cant be empty.") String name,
        @Email(message = "Invalid email.") String email,
        Date createdAt,
        @NotEmpty(message = "Phone cant be empty.") String phone,
        String profilePicture,
        String bannerPicture,
        Boolean isNGO,
        @NotEmpty(message = "State cant be empty.") String state,
        @NotEmpty(message = "City cant be empty.") String city,
        List<String> roles

) { }
