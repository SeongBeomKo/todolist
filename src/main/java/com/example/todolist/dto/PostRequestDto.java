package com.example.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostRequestDto {

    private String title;
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate duedate;
}
