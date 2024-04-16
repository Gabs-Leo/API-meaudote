package com.gabsleo.meaudote.dtos;

import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.enums.Species;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record AdoptionAnimalDto(
        UUID id,
        @NotEmpty(message = "Name cant be empty.") String name,
        @NotEmpty(message = "Description cant be empty.") String description,
        @Positive(message = "Age must be greater than 0.") @NotEmpty(message = "Age cant be empty.") Integer age,
        Boolean adopted,
        String image,
        @NotEmpty(message = "City cant be empty.") String city,
        @NotEmpty(message = "State cant be empty.") String state,
        @NotEmpty(message = "Weight cant be empty.") Float weight,
        @NotEmpty(message = "Species cant be empty.") Species species
) { }
