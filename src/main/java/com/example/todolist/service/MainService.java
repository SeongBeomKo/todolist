package com.example.todolist.service;

import com.example.todolist.dto.DataRequestDto;
import com.example.todolist.dto.DataResponseDto;
import com.example.todolist.dto.PostRequestDto;
import com.example.todolist.dto.UpdateRequestDto;
import com.example.todolist.model.Priority;
import com.example.todolist.model.Status;
import com.example.todolist.model.TodoList;
import com.example.todolist.repository.MemoryTodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainService {

    private final MemoryTodoListRepository memoryTodoListRepository;

    public List<DataResponseDto> showTodos(DataRequestDto dataRequestDto) {
        List<TodoList> todoLists = memoryTodoListRepository
                .getAllTodosByDueDateOrderByPriorityAndRank(dataRequestDto.getDuedate());

        List<DataResponseDto> dataResponseDtos = new ArrayList<>();
        for(TodoList todoList : todoLists) {
            dataResponseDtos.add(
                    DataResponseDto.builder()
                            .id(todoList.getId())
                            .description(todoList.getDescription())
                            .dueDate(todoList.getDueDate())
                            .priority(todoList.getPriority().getPriority())
                            .rank(todoList.getRank())
                            .title(todoList.getTitle())
                            .status(todoList.getStatus().getStatus())
                            .build()
            );
        }
        return dataResponseDtos;
    }

    public Long addTodo(PostRequestDto dataRequestDto) {

        List<TodoList> todoLists = memoryTodoListRepository
                .getAllTodosByDueDateOrderByPriorityAndRank(dataRequestDto.getDuedate());

        List<TodoList> todoListB = todoLists.stream()
                .filter(c -> c.getPriority().getPriority().equals("B"))
                .collect(Collectors.toList());

        TodoList newTodoList = TodoList.builder()
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .description(dataRequestDto.getDescription())
                .dueDate(dataRequestDto.getDuedate())
                .priority(Priority.B)
                .rank(todoListB.size())
                .title(dataRequestDto.getTitle())
                .status(Status.ONGOING)
                .build();

        return memoryTodoListRepository.save(newTodoList).getId();
    }

    public void removeTodo(Long id, LocalDate date) {

        TodoList todoList = memoryTodoListRepository
                .findByDueDateAndId(date, id);

        memoryTodoListRepository.deleteById(id, date);

        List<TodoList> todoLists = memoryTodoListRepository
                .getAllTodosByDueDateOrderByPriorityAndRank(date);
        List<TodoList> todoListBefore = todoLists.stream()
                .filter(c -> c.getPriority().getPriority().equals(todoList.getPriority().getPriority()))
                .collect(Collectors.toList());

        for(TodoList todoList1 : todoListBefore) {
            if(todoList1.getRank() > todoList.getRank()) {
                todoList1.setRank(todoList1.getRank() - 1);
                memoryTodoListRepository.update(todoList1);
            }
        }


    }

    public DataResponseDto updateTodo(Long id, UpdateRequestDto dataRequestDto) {

        TodoList todoList = memoryTodoListRepository
                .findByDueDateAndId(dataRequestDto.getDuedate(), id);

        removeTodo(id, dataRequestDto.getDuedate());

        if(!dataRequestDto.getPriority().equals(todoList.getPriority().getPriority())) {
            helpPriorityAndRankUpdate(dataRequestDto, todoList);
        } else {
            if(dataRequestDto.getRank() != todoList.getRank()) {
                helpPriorityAndRankUpdate(dataRequestDto, todoList);
            }
        }

        todoList.update(dataRequestDto.getTitle(),
                dataRequestDto.getDescription(),
                Status.EnumConverter(dataRequestDto.getStatus()),
                dataRequestDto.getRank(),
                Priority.valueOf(dataRequestDto.getPriority()));

        memoryTodoListRepository.update(todoList);

        return DataResponseDto.builder()
                .title(dataRequestDto.getTitle())
                .rank(dataRequestDto.getRank())
                .id(todoList.getId())
                .priority(dataRequestDto.getPriority())
                .dueDate(dataRequestDto.getDuedate())
                .description(dataRequestDto.getDescription())
                .status(dataRequestDto.getStatus())
                .build();
    }

    public void helpPriorityAndRankUpdate(UpdateRequestDto dataRequestDto, TodoList todoList) {
        List<TodoList> todoLists = memoryTodoListRepository
                .getAllTodosByDueDateOrderByPriorityAndRank(dataRequestDto.getDuedate());

        List<TodoList> todoListAfter = todoLists.stream()
                .filter(c -> c.getPriority().getPriority()
                        .equals(dataRequestDto.getPriority()))
                .collect(Collectors.toList());

        if(todoListAfter.size() < dataRequestDto.getRank()) {
            dataRequestDto
                    .setRank(dataRequestDto.getRank() -
                            (dataRequestDto.getRank() - todoListAfter.size()));
        } else {
            for(TodoList todoList1 : todoListAfter) {
                if(todoList1.getRank() <= todoList.getRank()) {
                    todoList1.setRank(todoList1.getRank() + 1);
                    memoryTodoListRepository.update(todoList1);
                }
            }
        }
    }
}
