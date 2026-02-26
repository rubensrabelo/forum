package io.github.rubensrabelo.project.forum.modules.user.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.rubensrabelo.project.forum.modules.user.application.dto.LoginRequestDTO;
import io.github.rubensrabelo.project.forum.modules.user.application.dto.RegisterRequestDTO;
import io.github.rubensrabelo.project.forum.modules.user.application.dto.ResponseDTO;
import io.github.rubensrabelo.project.forum.modules.user.application.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
        return ResponseEntity.ok(authService.login(body));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        return ResponseEntity.ok(authService.register(body));
    }
}