package io.github.rubensrabelo.project.forum.unit.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import io.github.rubensrabelo.project.forum.modules.user.application.dto.UserResponseDTO;
import io.github.rubensrabelo.project.forum.modules.user.application.dto.UserUpdateDTO;
import io.github.rubensrabelo.project.forum.modules.user.application.services.IUserService;
import io.github.rubensrabelo.project.forum.modules.user.application.services.UserService;
import io.github.rubensrabelo.project.forum.modules.user.domain.User;
import io.github.rubensrabelo.project.forum.modules.user.infra.repositories.IUserRepository;
import io.github.rubensrabelo.project.forum.shared.exceptions.DatabaseException;
import io.github.rubensrabelo.project.forum.shared.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserRepository repository;

    private IUserService userService;

    private User user;

    @BeforeEach
    void setup() {

        userService = new UserService(repository);

        user = new User();
        user.setId(1L);
        user.setFirstname("Rubens");
        user.setLastname("Rabelo");
        user.setEmail("rubens@email.com");
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
    }

    @Test
    void shouldFindUserById() {

        when(repository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDTO response = userService.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Rubens", response.firstname());
        assertEquals("Rabelo", response.lastname());
        assertEquals("rubens@email.com", response.email());

        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowWhenUserNotFound() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.findById(1L);
        });
    }

    @Test
    void shouldUpdateUser() {

        UserUpdateDTO dto = new UserUpdateDTO(
                "NovoNome",
                "NovoSobrenome",
                "novo@email.com"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenReturn(user);

        UserResponseDTO response = userService.update(1L, dto);

        assertNotNull(response);
        verify(repository).save(any(User.class));
    }

    @Test
    void shouldDeleteUser() {

        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> {
            userService.delete(1L);
        });

        verify(repository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeleteUserNotFound() {

        doThrow(EmptyResultDataAccessException.class)
                .when(repository).deleteById(1L);

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.delete(1L);
        });
    }

    @Test
    void shouldThrowDatabaseExceptionWhenIntegrityViolation() {

        doThrow(DataIntegrityViolationException.class)
                .when(repository).deleteById(1L);

        assertThrows(DatabaseException.class, () -> {
            userService.delete(1L);
        });
    }
}