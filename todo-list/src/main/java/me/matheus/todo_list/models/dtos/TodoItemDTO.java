package me.matheus.todo_list.models.dtos;

import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.util.UUID;

public record TodoItemDTO(UUID uuid, String title, String description, LocalDateTime creationDate, LocalDateTime dueDate, Boolean completed) { }