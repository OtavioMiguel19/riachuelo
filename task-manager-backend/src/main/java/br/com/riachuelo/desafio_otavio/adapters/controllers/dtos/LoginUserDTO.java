package br.com.riachuelo.desafio_otavio.adapters.controllers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginUserDTO(
        @Schema(description = "User's login email address", example = "user@example.com")
        String email,

        @Schema(description = "User's password", example = "password123")
        String password
) {
}