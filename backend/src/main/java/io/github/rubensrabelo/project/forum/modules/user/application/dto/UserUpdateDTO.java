package io.github.rubensrabelo.project.forum.modules.user.application.dto;

public record UserUpdateDTO(
        String firstname,
        String lastname,
        String email
        ) {
}
