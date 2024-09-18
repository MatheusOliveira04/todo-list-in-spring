package me.matheus.todo_list.services.impl;

import me.matheus.todo_list.models.TodoItem;
import me.matheus.todo_list.repositories.TodoItemRepository;
import me.matheus.todo_list.services.TodoItemService;
import me.matheus.todo_list.services.exceptions.IntegrityViolationException;
import me.matheus.todo_list.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TodoItemServiceImpl implements TodoItemService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Override
    public List<TodoItem> findAll(Pageable pageable) {
        Page<TodoItem> items = todoItemRepository.findAll(pageable);
        if(items.isEmpty()) {
            throw new ObjectNotFoundException("Todo items not found");
        }
        return items.toList();
    }

    @Override
    public TodoItem findById(UUID uuid) {
        return todoItemRepository.findById(uuid).orElseThrow(() -> new ObjectNotFoundException("Todo item not found with uuid: " + uuid.toString()));
    }

    @Override
    public TodoItem insert(TodoItem todoItem) {
        this.validations(todoItem);
        return todoItemRepository.save(todoItem);
    }

    @Override
    public TodoItem update(TodoItem todoItem) {
        this.findById(todoItem.getUuid());
        this.validations(todoItem);
        return this.todoItemRepository.save(todoItem);
    }

    @Override
    public void delete(UUID uuid) {
        todoItemRepository.delete(this.findById(uuid));
    }

    private void validations(TodoItem todoItem) {
        this.validFieldTitle(todoItem);
        this.validFieldDescription(todoItem);
    }

    private void validFieldTitle(TodoItem todoItem) {
        this.verifyTitleIsEmptyOrNull(todoItem);
    }

    private void verifyTitleIsEmptyOrNull(TodoItem todoItem) {
        if(todoItem.getTitle() == null || todoItem.getTitle().isEmpty()) {
            throw new IntegrityViolationException("The title must be filled in");
        }
    }

    private void validFieldDescription(TodoItem todoItem) {
        this.verifyDescriptionIsEmptyOrNull(todoItem);
        this.verifyDescriptionFieldLength(todoItem);
    }

    private void verifyDescriptionIsEmptyOrNull(TodoItem todoItem) {
        if(todoItem.getDescription() == null || todoItem.getDescription().isEmpty()) {
            throw new IntegrityViolationException("The description must be filled in");
        }
    }

    private void verifyDescriptionFieldLength(TodoItem todoItem) {
        if (todoItem.getDescription().length() > 200) {
            throw new IntegrityViolationException("The description must be longer than 200 characters");
        }
    }
}
