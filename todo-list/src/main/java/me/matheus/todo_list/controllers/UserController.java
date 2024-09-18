package me.matheus.todo_list.controllers;

import me.matheus.todo_list.models.User;
import me.matheus.todo_list.models.dtos.UserDTO;
import me.matheus.todo_list.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Secured({"ROLE_USER"})
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(@PageableDefault(size = 20, page = 0) Pageable pageable) {
        return ResponseEntity.ok(this.userService.findAll(pageable)
                .stream()
                .map(user -> new UserDTO(user.getUuid(), user.getName(), user.getEmail(), user.getPassword(), user.getRoles()))
                .toList());
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/{uuid}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID uuid) {
        User user = this.userService.findById(uuid);
        return ResponseEntity.ok(new UserDTO(user.getUuid(), user.getName(), user.getEmail(), user.getPassword(), user.getRoles()));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO, UriComponentsBuilder uriComponentsBuilder) {
        User user = this.userService.insert(new User(userDTO.uuid(), userDTO.name(), userDTO.email(), userDTO.password(), userDTO.roles()));
        URI uri = uriComponentsBuilder.path("/users/{uuid}").buildAndExpand(userDTO.uuid()).toUri();
        return ResponseEntity.created(uri).body(new UserDTO(user.getUuid(), user.getName(), user.getEmail(), user.getPassword(), user.getRoles()));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{uuid}")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO, @PathVariable UUID uuid) {
        User user = this.userService.update(new User(userDTO.uuid(), userDTO.name(), userDTO.email(), userDTO.password(), userDTO.roles()));
        return ResponseEntity.ok(new UserDTO(uuid, user.getName(), user.getEmail(), user.getPassword(), user.getRoles()));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        this.userService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

}
