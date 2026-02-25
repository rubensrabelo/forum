package io.github.rubensrabelo.project.forum.modules.user.application.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.rubensrabelo.project.forum.modules.user.application.dto.LoginRequestDTO;
import io.github.rubensrabelo.project.forum.modules.user.application.dto.RegisterRequestDTO;
import io.github.rubensrabelo.project.forum.modules.user.application.dto.ResponseDTO;
import io.github.rubensrabelo.project.forum.modules.user.domain.User;
import io.github.rubensrabelo.project.forum.modules.user.infra.repositories.IUserRepository;
import io.github.rubensrabelo.project.forum.modules.user.infra.security.TokenService;

@Service
public class AuthService {

    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(
            IUserRepository repository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public ResponseDTO login(LoginRequestDTO body) {
        User user = repository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(body.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = tokenService.generate(user);

        return new ResponseDTO(
                user.getFirstname(),
                user.getLastname(),
                token);
    }

    public ResponseDTO register(RegisterRequestDTO body) {
        Optional<User> user = repository.findByEmail(body.email());

        if (user.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setEmail(body.email());
        newUser.setFirstname(body.firstname());
        newUser.setLastname(body.lastname());

        newUser = repository.save(newUser);

        String token = tokenService.generate(newUser);

        return new ResponseDTO(
                newUser.getFirstname(),
                newUser.getLastname(),
                token);
    }
}