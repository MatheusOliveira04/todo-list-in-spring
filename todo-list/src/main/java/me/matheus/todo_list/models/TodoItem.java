package me.matheus.todo_list.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid")
@Getter
@Entity(name = "tb_todo-item")
public class TodoItem {

    @Id
    @GeneratedValue
    @Setter
    private UUID uuid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    private LocalDateTime dueDate;

    @Column(nullable = false)
    private Boolean completed = false;
}
