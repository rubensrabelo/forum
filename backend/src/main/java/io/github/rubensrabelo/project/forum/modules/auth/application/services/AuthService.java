package io.github.rubensrabelo.project.forum.modules.auth.application.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.rubensrabelo.project.forum.modules.auth.application.dto.LoginRequestDTO;
import io.github.rubensrabelo.project.forum.modules.auth.application.dto.RegisterRequestDTO;
import io.github.rubensrabelo.project.forum.modules.auth.application.dto.AuthResponseDTO;
import io.github.rubensrabelo.project.forum.modules.auth.infra.security.TokenService;
import io.github.rubensrabelo.project.forum.modules.user.application.services.IUserService;
import io.github.rubensrabelo.project.forum.modules.user.domain.User;
import io.github.rubensrabelo.project.forum.shared.exceptions.InvalidCredentialsException;
import io.github.rubensrabelo.project.forum.shared.exceptions.ResourceNotFoundException;
import io.github.rubensrabelo.project.forum.shared.exceptions.UserAlreadyExistsException;

@Service
public class AuthService implements IAuthService {

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(
            IUserService userService,
            PasswordEncoder passwordEncoder,
            TokenService tokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO data) {

        User entity = userService.findByEmail(data.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(data.password(), entity.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = tokenService.generate(entity);

        return buildResponse(entity, token);
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO data) {

        if (userService.findByEmail(data.email()).isPresent()) {
            throw new UserAlreadyExistsException(data.email());
        }

        User user = new User();
        user.setEmail(data.email());
        user.setFirstname(data.firstname());
        user.setLastname(data.lastname());
        user.setPassword(passwordEncoder.encode(data.password()));

        user = userService.save(user);

        String token = tokenService.generate(user);

        return buildResponse(user, token);
    }

    private AuthResponseDTO buildResponse(User user, String token) {
        return new AuthResponseDTO(
                user.getFirstname(),
                user.getLastname(),
                token
        );
    }
}