package com.example.todolist.controller;

import com.example.todolist.dto.DataRequestDto;
import com.example.todolist.dto.PostRequestDto;
import com.example.todolist.dto.DataResponseDto;
import com.example.todolist.dto.UpdateRequestDto;
import com.example.todolist.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainController {

    private final MainService mainService;

    @GetMapping("/tasks")
    public List<DataResponseDto> showTodos(@RequestBody DataRequestDto dataRequestDto) {
        return mainService.showTodos(dataRequestDto);
    }

    @PostMapping("/newTask")
    public Long addTodo( @RequestBody PostRequestDto dataRequestDto) {
        return mainService.addTodo(dataRequestDto);
    }

    @DeleteMapping("/task/{id}")
    public void removeTodo(@PathVariable Long id, @RequestBody DataRequestDto dataRequestDto) {
        mainService.removeTodo(id, dataRequestDto);
    }

    @PutMapping("/task/{id}")
    public DataResponseDto updateTodo(@PathVariable Long id, @RequestBody UpdateRequestDto dataRequestDto) {
        return mainService.updateTodo(id, dataRequestDto);
    }
}
