package io.github.rubensrabelo.project.forum.modules.auth.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(

        @NotBlank(message = "Firstname is required")
        @Size(min = 2, max = 50, message = "Firstname must be between 2 and 50 characters")
        String firstname,

        @NotBlank(message = "Lastname is required")
        @Size(min = 2, max = 50, message = "Lastname must be between 2 and 50 characters")
        String lastname,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password
) {}