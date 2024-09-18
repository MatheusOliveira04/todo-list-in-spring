package me.matheus.todo_list.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(of = "uuid")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "tb_user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "uuid_user")
    private UUID uuid;

    @Column(name = "name_user")
    private String name;

    @Column(name = "email_user")
    private String email;

    @Column(name = "password_user")
    private String password;

    @Column(name = "roles_user")
    private String roles;
}
