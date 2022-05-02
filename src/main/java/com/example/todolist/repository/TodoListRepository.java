package com.example.todolist.repository;

import com.example.todolist.model.TodoList;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TodoListRepository {

    TodoList save(TodoList todoList);
    void deleteById(Long id, LocalDate date);
    List<TodoList> findByDueDate(LocalDate date);
    TodoList findByDueDateAndId(LocalDate localDate, Long id);
    List<TodoList> getAllTodosByDueDateOrderByPriorityAndRank(LocalDate date);
    TodoList update(TodoList todoList);
}
