package com.example.todolist.repository;

import com.example.todolist.model.TodoList;

import java.time.LocalDateTime;
import java.util.Comparator;

public interface MyUtils {
    Comparator<LocalDateTime> localDateTimeComparator = (o1, o2) -> o1.compareTo(o2);
    Comparator<TodoList> rankComparator = (o1, o2) -> o1.getRank() - o2.getRank();
}
