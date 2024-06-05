package com.gabsleo.meaudote.dtos;

import jakarta.validation.constraints.NotEmpty;

public record UpdateAppUserDto(
        String name,
        @NotEmpty(message = "Phone cant be empty.")
        String phone,
        Boolean isNGO,
        @NotEmpty(message = "State cant be empty.")
        String state,
        @NotEmpty(message = "City cant be empty.")
        String city
) {  }
