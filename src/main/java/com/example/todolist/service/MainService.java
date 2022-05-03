package com.example.todolist.service;

import com.example.todolist.dto.request.DataRequestDto;
import com.example.todolist.dto.response.DataResponseDto;
import com.example.todolist.dto.request.PostRequestDto;
import com.example.todolist.dto.request.UpdateRequestDto;
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

    /* 할일 조회 */
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

    /* 할일 생성 */
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

    /* 할일 삭제 */
    public void removeTodo(Long id, LocalDate date) {

        TodoList todoList = memoryTodoListRepository
                .findByDueDateAndId(date, id);

        memoryTodoListRepository.deleteById(id, date);

        List<TodoList> todoLists = memoryTodoListRepository
                .getAllTodosByDueDateOrderByPriorityAndRank(date);
        List<TodoList> todoListBefore = todoLists.stream()
                .filter(c -> c.getPriority().getPriority().equals(todoList.getPriority().getPriority()))
                .collect(Collectors.toList());

        /* 중간 순서 삭제 시*/
        for(TodoList todoList1 : todoListBefore) {
            if(todoList1.getRank() > todoList.getRank()) {
                todoList1.setRank(todoList1.getRank() - 1);
                memoryTodoListRepository.update(todoList1);
            }
        }


    }

    /* 할일 수정 */
    public DataResponseDto updateTodo(Long id, UpdateRequestDto dataRequestDto) {

        TodoList todoList = memoryTodoListRepository
                .findByDueDateAndId(dataRequestDto.getDuedate(), id);

        removeTodo(id, dataRequestDto.getDuedate());

        /* 중요도 수정 발생 시 */
        if(!dataRequestDto.getPriority().equals(todoList.getPriority().getPriority())) {
            helpPriorityAndRankUpdate(dataRequestDto, todoList);
        } else {
            /* 중요도 수정은 발생하지 않고 순서만 수정 발생 시 */
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

    /* 할일 수정 시 중요도와 순서 재정렬*/
    public void helpPriorityAndRankUpdate(UpdateRequestDto dataRequestDto, TodoList todoList) {
        List<TodoList> todoLists = memoryTodoListRepository
                .getAllTodosByDueDateOrderByPriorityAndRank(dataRequestDto.getDuedate());

        List<TodoList> todoListAfter = todoLists.stream()
                .filter(c -> c.getPriority().getPriority()
                        .equals(dataRequestDto.getPriority()))
                .collect(Collectors.toList());

        /* 순서 누락 제거*/
        if(todoListAfter.size() < dataRequestDto.getRank()) {
            dataRequestDto
                    .setRank(dataRequestDto.getRank() -
                            (dataRequestDto.getRank() - todoListAfter.size()));
        } else {
            /* 순서 중간 삽입 시*/
            for(TodoList todoList1 : todoListAfter) {
                if(todoList1.getRank() <= todoList.getRank()) {
                    todoList1.setRank(todoList1.getRank() + 1);
                    memoryTodoListRepository.update(todoList1);
                }
            }
        }
    }
}
