package io.github.rubensrabelo.project.forum.modules.user.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.github.rubensrabelo.project.forum.modules.user.application.dto.UserResponseDTO;
import io.github.rubensrabelo.project.forum.modules.user.application.dto.UserUpdateDTO;
import io.github.rubensrabelo.project.forum.modules.user.application.services.IUserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping(value ="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponseDTO> findById(@PathVariable long id) {
        UserResponseDTO response = service.findById(id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(
        value = "/{id}",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<UserResponseDTO> udpate(@Valid @PathVariable long id,  @RequestBody UserUpdateDTO data) {
        UserResponseDTO response = service.udpate(id, data);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value ="/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); 
    }
}
