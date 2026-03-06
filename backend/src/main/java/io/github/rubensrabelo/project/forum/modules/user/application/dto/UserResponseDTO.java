package io.github.rubensrabelo.project.forum.modules.user.application.dto;

import java.time.Instant;

public record UserResponseDTO(
        long id,
        String firstname,
        String lastname,
        String email,
        Instant createdAt,
        Instant updatedAt) {
}
