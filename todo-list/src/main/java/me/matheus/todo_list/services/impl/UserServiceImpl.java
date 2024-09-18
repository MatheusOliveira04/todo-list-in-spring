package me.matheus.todo_list.services.impl;

import me.matheus.todo_list.models.User;
import me.matheus.todo_list.repositories.UserRepository;
import me.matheus.todo_list.services.UserService;
import me.matheus.todo_list.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        if(users.isEmpty()) {
            throw new ObjectNotFoundException("Users not found");
        }
        return users.toList();
    }

    @Override
    public User findById(UUID uuid) {
        return userRepository.findById(uuid).orElseThrow(() -> new ObjectNotFoundException("User not found with uuid: " + uuid.toString()));
    }

    @Override
    public User insert(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        this.userRepository.findById(user.getUuid());
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID uuid) {
        userRepository.delete(this.findById(uuid));
    }
}
