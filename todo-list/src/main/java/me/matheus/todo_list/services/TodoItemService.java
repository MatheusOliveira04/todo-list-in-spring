package me.matheus.todo_list.services;

import me.matheus.todo_list.models.TodoItem;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TodoItemService {

    List<TodoItem> findAll(Pageable pageable);

    TodoItem findById(UUID uuid);

    TodoItem insert(TodoItem todoItem);

    TodoItem update(TodoItem todoItem);

    void delete(UUID uuid);
}
