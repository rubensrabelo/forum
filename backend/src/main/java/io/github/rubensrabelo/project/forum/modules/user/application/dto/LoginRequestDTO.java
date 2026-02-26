package io.github.rubensrabelo.project.forum.modules.user.application.dto;

public record LoginRequestDTO(
        String email,
        String password) {
}
