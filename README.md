# My first TODO LIST using Spring Boot 

Project created for completing the DIO course.

```mermaid

classDiagram
    class TodoList {
        uuid: String
        title: String
        description: String
        creationDate: DateTime
        dueDate: DateTime
        completed: Boolean
    }

    class User {
        uuid: String
        name: String
        email: String
        password: String
        roles: String
    }
```
