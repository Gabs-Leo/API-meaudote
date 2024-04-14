package com.gabsleo.meaudote.dtos;

import jakarta.validation.constraints.Email;

public record EmailDto(@Email(message = "Invalid email.") String email) {
}
