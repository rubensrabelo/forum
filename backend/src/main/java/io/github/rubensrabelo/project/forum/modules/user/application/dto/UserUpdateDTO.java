package io.github.rubensrabelo.project.forum.modules.user.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(

        @NotBlank(message = "Firstname is required")
        @Size(min = 2, max = 50)
        String firstname,

        @NotBlank(message = "Lastname is required")
        @Size(min = 2, max = 50)
        String lastname,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email
) {}