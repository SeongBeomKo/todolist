package com.example.todolist.controller;

import com.example.todolist.dto.request.DataRequestDto;
import com.example.todolist.dto.request.PostRequestDto;
import com.example.todolist.dto.response.DataResponseDto;
import com.example.todolist.dto.request.UpdateRequestDto;
import com.example.todolist.exception.InputValidator;
import com.example.todolist.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/newtask")
    public Long addTodo(@RequestBody @Valid PostRequestDto dataRequestDto,
                        BindingResult bindingResult) {
        InputValidator.BadRequestHandler(bindingResult);
        return mainService.addTodo(dataRequestDto);
    }

    @DeleteMapping("/task/{id}")
    public void removeTodo(@PathVariable Long id,
                           @RequestBody DataRequestDto dataRequestDto) {
        mainService.removeTodo(id, dataRequestDto.getDuedate());
    }

    @PutMapping("/task/{id}")
    public DataResponseDto updateTodo(@PathVariable Long id,
                                      @RequestBody @Valid UpdateRequestDto dataRequestDto,
                                      BindingResult bindingResult) {
        InputValidator.BadRequestHandler(bindingResult);
        return mainService.updateTodo(id, dataRequestDto);
    }
}
