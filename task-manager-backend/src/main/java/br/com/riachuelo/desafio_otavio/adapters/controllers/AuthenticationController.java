package br.com.riachuelo.desafio_otavio.adapters.controllers;

import br.com.riachuelo.desafio_otavio.adapters.controllers.dtos.LoginResponseDTO;
import br.com.riachuelo.desafio_otavio.adapters.controllers.dtos.LoginUserDTO;
import br.com.riachuelo.desafio_otavio.adapters.controllers.dtos.RegisterUserDTO;
import br.com.riachuelo.desafio_otavio.adapters.controllers.dtos.UserDTO;
import br.com.riachuelo.desafio_otavio.adapters.models.UserModel;
import br.com.riachuelo.desafio_otavio.configs.JwtService;
import br.com.riachuelo.desafio_otavio.adapters.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@Tag(name = "Authentication", description = "Authentication operations")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("register")
    @Operation(summary = "Registers a new user", description = "Registers a new user with name, email and password.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User registered successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Could not register new user."
            ),
    })
    ResponseEntity<UserDTO> register(@RequestBody RegisterUserDTO dto) {
        UserModel user = authenticationService.signup(dto);
        return ResponseEntity.ok(UserDTO.fromModel(user));
    }

    @PostMapping("login")
    @Operation(summary = "Login user", description = "Realizes the user login, returning a valid JWT token.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User logged in successfully"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "User failed logging in"
            ),
    })
    ResponseEntity<LoginResponseDTO> login(@RequestBody LoginUserDTO dto) {
        UserModel user = authenticationService.authenticate(dto);
        String token = jwtService.generateToken(user);
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponseDTO);
    }
}
