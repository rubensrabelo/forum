package io.github.rubensrabelo.project.forum.modules.user.application.services;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import io.github.rubensrabelo.project.forum.modules.user.application.dto.UserResponseDTO;
import io.github.rubensrabelo.project.forum.modules.user.application.dto.UserUpdateDTO;
import io.github.rubensrabelo.project.forum.modules.user.domain.User;
import io.github.rubensrabelo.project.forum.modules.user.infra.repositories.IUserRepository;
import io.github.rubensrabelo.project.forum.shared.exceptions.DatabaseException;
import io.github.rubensrabelo.project.forum.shared.exceptions.ResourceNotFoundException;

@Service
public class UserService implements IUserService {

    private final IUserRepository repository;

    public UserService(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserResponseDTO findById(long id) {
        User entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return mapToResponse(entity);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public UserResponseDTO udpate(long id, UserUpdateDTO data) {
        User entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        updateData(data, entity);

        User updatedUser = repository.save(entity);

        return mapToResponse(updatedUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void delete(long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Collaborator not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private UserResponseDTO mapToResponse(User entity) {
        return new UserResponseDTO(
            entity.getId(),
            entity.getFirstname(),
            entity.getLastname(),
            entity.getEmail(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    private void updateData(UserUpdateDTO data, User entity) {
        entity.setFirstname(data.firstname());
        entity.setLastname(data.lastname());
        entity.setEmail(data.email());
    }
}