package me.matheus.todo_list.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record UserDTO(UUID uuid, String name, String email, @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) String password, String roles) {
}
