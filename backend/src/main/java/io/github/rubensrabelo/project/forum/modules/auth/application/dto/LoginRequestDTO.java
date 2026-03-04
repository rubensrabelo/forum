package io.github.rubensrabelo.project.forum.modules.auth.application.dto;

public record LoginRequestDTO(
        String email,
        String password) {
}
