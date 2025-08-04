package br.com.riachuelo.desafio_otavio.adapters.services;

import br.com.riachuelo.desafio_otavio.adapters.controllers.dtos.LoginUserDTO;
import br.com.riachuelo.desafio_otavio.adapters.controllers.dtos.RegisterUserDTO;
import br.com.riachuelo.desafio_otavio.adapters.models.UserModel;
import br.com.riachuelo.desafio_otavio.adapters.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserModel userModel;

    @Test
    void testSignupShouldEncodePasswordAndSaveUser() {
        RegisterUserDTO dto = new RegisterUserDTO("otavio@email.com", "123456", "Otavio");
        String encodedPassword = "encoded_123456";

        when(passwordEncoder.encode(dto.password())).thenReturn(encodedPassword);
        when(usersRepository.save(any(UserModel.class))).thenReturn(userModel);

        UserModel result = authenticationService.signup(dto);

        ArgumentCaptor<UserModel> captor = ArgumentCaptor.forClass(UserModel.class);
        verify(usersRepository).save(captor.capture());

        UserModel savedUser = captor.getValue();
        assertEquals(dto.fullName(), savedUser.getFullName());
        assertEquals(dto.email(), savedUser.getEmail());
        assertEquals(encodedPassword, savedUser.getPassword());

        assertEquals(userModel, result);
    }

    @Test
    void testAuthenticateShouldCallAuthenticationManagerAndReturnUser() {
        LoginUserDTO dto = new LoginUserDTO("otavio@email.com", "123456");

        when(usersRepository.findByEmail(dto.email())).thenReturn(Optional.of(userModel));

        UserModel result = authenticationService.authenticate(dto);

        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );

        verify(usersRepository).findByEmail(dto.email());
        assertEquals(userModel, result);
    }

    @Test
    void testGetLoggedUserShouldReturnUserFromContext() {
        SecurityContext context = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userModel);
        when(context.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(context);

        UserModel result = authenticationService.getLoggedUser();

        assertEquals(userModel, result);
    }

    @Test
    void testGetLoggedUserShouldReturnNullIfNotAuthenticated() {
        SecurityContext context = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.isAuthenticated()).thenReturn(false);
        when(context.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(context);

        UserModel result = authenticationService.getLoggedUser();

        assertNull(result);
    }
}