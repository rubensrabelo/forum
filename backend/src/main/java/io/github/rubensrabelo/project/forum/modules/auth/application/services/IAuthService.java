package io.github.rubensrabelo.project.forum.modules.auth.application.services;

import io.github.rubensrabelo.project.forum.modules.auth.application.dto.LoginRequestDTO;
import io.github.rubensrabelo.project.forum.modules.auth.application.dto.RegisterRequestDTO;
import io.github.rubensrabelo.project.forum.modules.auth.application.dto.AuthResponseDTO;

public interface IAuthService {

    AuthResponseDTO login(LoginRequestDTO body);
    AuthResponseDTO register(RegisterRequestDTO body);
}