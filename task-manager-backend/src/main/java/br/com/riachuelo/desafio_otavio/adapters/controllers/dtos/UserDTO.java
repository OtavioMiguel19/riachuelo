package br.com.riachuelo.desafio_otavio.adapters.controllers.dtos;

import br.com.riachuelo.desafio_otavio.adapters.models.UserModel;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public record UserDTO(
        @Schema(description = "Unique identifier of the user", example = "1")
        long id,

        @Schema(description = "Full name of the user", example = "Foo Bar")
        String fullName,

        @Schema(description = "User's email address", example = "foo@bar.com")
        String email,

        @Schema(description = "User account creation date in ISO format (yyyy-MM-dd)", example = "2025-08-03")
        String createdDate,

        @Schema(description = "User account last update date in ISO format (yyyy-MM-dd)", example = "2025-08-03")
        String updatedDate,

        @Schema(description = "Whether the user account is enabled", example = "true")
        boolean enabled,

        @Schema(description = "Whether the user account is not expired", example = "true")
        boolean accountNonExpired,

        @Schema(description = "Whether the user account is not locked", example = "true")
        boolean accountNonLocked,

        @Schema(description = "Whether the user credentials are not expired", example = "true")
        boolean credentialsNonExpired,

        @Schema(description = "Username used for authentication (usually same as email)", example = "foo@bar.com")
        String username,

        @Schema(description = "User roles or authorities", example = "[]")
        List<String> authorities
) {
    public static UserDTO fromModel(UserModel user) {
        return new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getCreatedAt().toString(),
                user.getUpdatedAt().toString(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                user.getUsername(),
                user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
    }
}