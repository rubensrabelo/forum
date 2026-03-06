package io.github.rubensrabelo.project.forum.unit.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.rubensrabelo.project.forum.modules.auth.application.dto.AuthResponseDTO;
import io.github.rubensrabelo.project.forum.modules.auth.application.dto.LoginRequestDTO;
import io.github.rubensrabelo.project.forum.modules.auth.application.dto.RegisterRequestDTO;
import io.github.rubensrabelo.project.forum.modules.auth.application.services.AuthService;
import io.github.rubensrabelo.project.forum.modules.auth.application.services.IAuthService;
import io.github.rubensrabelo.project.forum.modules.auth.infra.security.TokenService;
import io.github.rubensrabelo.project.forum.modules.user.application.services.IUserService;
import io.github.rubensrabelo.project.forum.modules.user.domain.User;
import io.github.rubensrabelo.project.forum.shared.exceptions.InvalidCredentialsException;
import io.github.rubensrabelo.project.forum.shared.exceptions.ResourceNotFoundException;
import io.github.rubensrabelo.project.forum.shared.exceptions.UserAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private IUserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    private IAuthService authService;

    private User user;

    @BeforeEach
    void setup() {

        authService = new AuthService(userService, passwordEncoder, tokenService);

        user = new User();
        user.setEmail("test@email.com");
        user.setFirstname("Rubens");
        user.setLastname("Rabelo");
        user.setPassword("encoded-password");
    }

    @Test
    void shouldLoginSuccessfully() {

        LoginRequestDTO request = new LoginRequestDTO(
                "test@email.com",
                "123456"
        );

        when(userService.findByEmail(request.email()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(request.password(), user.getPassword()))
                .thenReturn(true);

        when(tokenService.generate(user))
                .thenReturn("token-123");

        AuthResponseDTO response = authService.login(request);

        assertNotNull(response);
        assertEquals("Rubens", response.firstname());
        assertEquals("Rabelo", response.lastname());
        assertEquals("token-123", response.token());

        verify(userService).findByEmail(request.email());
        verify(tokenService).generate(user);
    }

    @Test
    void shouldThrowWhenUserNotFound() {

        LoginRequestDTO request = new LoginRequestDTO(
                "notfound@email.com",
                "123456"
        );

        when(userService.findByEmail(request.email()))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            authService.login(request);
        });
    }

    @Test
    void shouldThrowWhenPasswordInvalid() {

        LoginRequestDTO request = new LoginRequestDTO(
                "test@email.com",
                "wrong-password"
        );

        when(userService.findByEmail(request.email()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(request.password(), user.getPassword()))
                .thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> {
            authService.login(request);
        });
    }

    @Test
    void shouldRegisterSuccessfully() {

        RegisterRequestDTO request = new RegisterRequestDTO(
                "Rubens",
                "Rabelo",
                "test@email.com",
                "123456"
        );

        when(userService.findByEmail(request.email()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(request.password()))
                .thenReturn("encoded-password");

        when(userService.save(any(User.class)))
                .thenReturn(user);

        when(tokenService.generate(user))
                .thenReturn("token-123");

        AuthResponseDTO response = authService.register(request);

        assertNotNull(response);
        assertEquals("Rubens", response.firstname());
        assertEquals("Rabelo", response.lastname());
        assertEquals("token-123", response.token());

        verify(userService).save(any(User.class));
    }

    @Test
    void shouldThrowWhenUserAlreadyExists() {

        RegisterRequestDTO request = new RegisterRequestDTO(
                "Rubens",
                "Rabelo",
                "test@email.com",
                "123456"
        );

        when(userService.findByEmail(request.email()))
                .thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> {
            authService.register(request);
        });
    }
}