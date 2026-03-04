package io.github.rubensrabelo.project.forum.modules.auth.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.rubensrabelo.project.forum.modules.auth.application.dto.LoginRequestDTO;
import io.github.rubensrabelo.project.forum.modules.auth.application.dto.RegisterRequestDTO;
import io.github.rubensrabelo.project.forum.modules.auth.application.dto.AuthResponseDTO;
import io.github.rubensrabelo.project.forum.modules.auth.application.services.IAuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService service;

    public AuthController(IAuthService service) {
        this.service = service;
    }

    @PostMapping(
        value = "/login",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO body) {
        AuthResponseDTO response = service.login(body);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(
        value = "/register",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO body) {
        AuthResponseDTO response = service.register(body);
        return ResponseEntity.ok().body(response);
    }
}