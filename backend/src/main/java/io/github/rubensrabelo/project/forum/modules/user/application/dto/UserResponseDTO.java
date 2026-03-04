package io.github.rubensrabelo.project.forum.modules.user.application.dto;

import java.time.LocalDateTime;

public record UserResponseDTO(
        long id,
        String firstname,
        String lastname,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
