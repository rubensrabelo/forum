package io.github.rubensrabelo.project.forum.modules.auth.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.rubensrabelo.project.forum.modules.auth.application.dto.LoginRequestDTO;
import io.github.rubensrabelo.project.forum.modules.auth.application.dto.RegisterRequestDTO;
import io.github.rubensrabelo.project.forum.modules.auth.application.dto.AuthResponseDTO;
import io.github.rubensrabelo.project.forum.modules.auth.application.services.IAuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO body) {
        return ResponseEntity.ok(authService.login(body));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        return ResponseEntity.ok(authService.register(body));
    }
}