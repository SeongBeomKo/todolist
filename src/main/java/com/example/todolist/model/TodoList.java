package com.example.todolist.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TodoList {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private int rank;
    private Priority priority;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public void setId(Long sequence) {
        this.id = sequence;
    }

    public void update(String title, String description, Status status, int rank, Priority priority) {
        this.title = title;
        this.rank = rank;
        this.description = description;
        this.status = status;
        this.priority = priority;
    }
}
