package br.com.riachuelo.desafio_otavio.adapters.controllers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response containing the JWT")
public record LoginResponseDTO(
        @Schema(description = "JWT access token")
        String token,
        @Schema(description = "Token expiration time in seconds")
        long expirationInSeconds
) {
}