package io.github.rubensrabelo.project.forum.modules.auth.application.services;

import io.github.rubensrabelo.project.forum.modules.auth.application.dto.LoginRequestDTO;
import io.github.rubensrabelo.project.forum.modules.auth.application.dto.RegisterRequestDTO;
import io.github.rubensrabelo.project.forum.modules.auth.application.dto.ResponseDTO;

public interface IAuthService {

    ResponseDTO login(LoginRequestDTO body);
    ResponseDTO register(RegisterRequestDTO body);
}