package me.matheus.todo_list.services;

import me.matheus.todo_list.models.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> findAll(Pageable pageable);

    User findById(UUID uuid);

    User insert(User user);

    User update(User user);

    void delete(UUID uuid);
}
