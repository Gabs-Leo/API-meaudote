package com.gabsleo.meaudote.dtos;

import jakarta.validation.constraints.NotEmpty;

public record NameDto(@NotEmpty(message = "Name cant be empty.") String name) {
}
