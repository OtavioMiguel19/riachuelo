package br.com.riachuelo.desafio_otavio.adapters.controllers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterUserDTO(
        @Schema(description = "User's email address for registration", example = "user@example.com")
        String email,

        @Schema(description = "User's password", example = "password123")
        String password,

        @Schema(description = "User's full name", example = "John Doe")
        String fullName
) {
}