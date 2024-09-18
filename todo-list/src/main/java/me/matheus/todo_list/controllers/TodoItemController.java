package me.matheus.todo_list.controllers;

import me.matheus.todo_list.models.TodoItem;
import me.matheus.todo_list.models.dtos.TodoItemDTO;
import me.matheus.todo_list.services.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todo-items")
public class TodoItemController {

    @Autowired
    private TodoItemService todoItemService;

    @Secured({"ROLE_USER"})
    @GetMapping("/{uuid}")
    public ResponseEntity<TodoItemDTO> findById(@PathVariable UUID uuid) {
        TodoItem todoItem = this.todoItemService.findById(uuid);
        return ResponseEntity.ok(new TodoItemDTO(todoItem.getUuid(), todoItem.getTitle(), todoItem.getDescription(),
                todoItem.getCreationDate(), todoItem.getDueDate(), todoItem.getCompleted()));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<TodoItemDTO> insert(@RequestBody TodoItemDTO dto, UriComponentsBuilder uriComponentsBuilder) {
        TodoItem todoItem = this.todoItemService.insert(new TodoItem(null, dto.title(), dto.description(), dto.creationDate(), dto.dueDate(), dto.completed()));
        URI uri = uriComponentsBuilder.path("/todo-items/{uuid}").buildAndExpand(todoItem.getUuid()).toUri();
        return ResponseEntity.created(uri).body(new TodoItemDTO(todoItem.getUuid(), todoItem.getTitle(), todoItem.getDescription(),
                todoItem.getCreationDate(), todoItem.getDueDate(), todoItem.getCompleted()));
    }

    @Secured({"ROLE_USER"})
    @GetMapping
    public ResponseEntity<List<TodoItemDTO>> findAll(@PageableDefault(page = 0, size = 25, sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(this.todoItemService.findAll(pageable)
                .stream()
                .map(todoItem -> new TodoItemDTO(todoItem.getUuid(), todoItem.getTitle(), todoItem.getDescription(),
                todoItem.getCreationDate(), todoItem.getDueDate(), todoItem.getCompleted()))
                .toList());
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{uuid}")
    public ResponseEntity<TodoItemDTO> update(@RequestBody TodoItemDTO dto, @PathVariable UUID uuid) {
        this.todoItemService.update(new TodoItem(uuid, dto.title(), dto.description(), dto.creationDate(), dto.dueDate(), dto.completed()));
        return ResponseEntity.ok(new TodoItemDTO(uuid, dto.title(), dto.description(), dto.creationDate(), dto.dueDate(), dto.completed()));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        this.todoItemService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
