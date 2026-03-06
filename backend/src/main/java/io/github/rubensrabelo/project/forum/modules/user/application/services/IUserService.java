package io.github.rubensrabelo.project.forum.modules.user.application.services;

import java.util.Optional;

import io.github.rubensrabelo.project.forum.modules.user.application.dto.UserResponseDTO;
import io.github.rubensrabelo.project.forum.modules.user.application.dto.UserUpdateDTO;
import io.github.rubensrabelo.project.forum.modules.user.domain.User;

public interface IUserService {

    UserResponseDTO findById(long id);
    User save(User entity);
    UserResponseDTO udpate(long id, UserUpdateDTO data);
    Optional<User> findByEmail(String email);
    void delete(long id);
}
