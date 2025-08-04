package br.com.riachuelo.desafio_otavio.adapters.controllers;

import br.com.riachuelo.desafio_otavio.adapters.controllers.dtos.LoginResponseDTO;
import br.com.riachuelo.desafio_otavio.adapters.controllers.dtos.LoginUserDTO;
import br.com.riachuelo.desafio_otavio.adapters.controllers.dtos.RegisterUserDTO;
import br.com.riachuelo.desafio_otavio.adapters.controllers.dtos.UserDTO;
import br.com.riachuelo.desafio_otavio.adapters.models.UserModel;
import br.com.riachuelo.desafio_otavio.adapters.services.AuthenticationService;
import br.com.riachuelo.desafio_otavio.configs.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private JwtService jwtService;

    @Mock
    private RegisterUserDTO registerUserDTO;

    @Mock
    private LoginUserDTO loginUserDTO;

    @Test
    void register_ReturnsUserDTO() {
        UserModel userModel = mock(UserModel.class);
        UserDTO userDTO = mock(UserDTO.class);

        when(authenticationService.signup(registerUserDTO)).thenReturn(userModel);
        try (MockedStatic<UserDTO> mockedUserDTO = mockStatic(UserDTO.class)) {
            mockedUserDTO.when(() -> UserDTO.fromModel(userModel)).thenReturn(userDTO);

            ResponseEntity<UserDTO> response = authenticationController.register(registerUserDTO);

            assertEquals(200, response.getStatusCode().value());
            assertEquals(userDTO, response.getBody());

            verify(authenticationService).signup(registerUserDTO);
            mockedUserDTO.verify(() -> UserDTO.fromModel(userModel));
        }
    }

    @Test
    void login_ReturnsLoginResponseDTO() {
        UserModel userModel = mock(UserModel.class);
        String fakeToken = "fake.jwt.token";
        long expirationTime = 3600L;

        when(authenticationService.authenticate(loginUserDTO)).thenReturn(userModel);
        when(jwtService.generateToken(userModel)).thenReturn(fakeToken);
        when(jwtService.getExpirationTime()).thenReturn(expirationTime);

        ResponseEntity<LoginResponseDTO> response = authenticationController.login(loginUserDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(fakeToken, Objects.requireNonNull(response.getBody()).token());
        assertEquals(expirationTime, response.getBody().expirationInSeconds());

        verify(authenticationService).authenticate(loginUserDTO);
        verify(jwtService).generateToken(userModel);
        verify(jwtService).getExpirationTime();
    }
}