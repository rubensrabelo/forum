package io.github.rubensrabelo.project.forum.modules.auth.application.dto;

public record RegisterRequestDTO(
        String firstname,
        String lastname,
        String email,
        String password) {
}
