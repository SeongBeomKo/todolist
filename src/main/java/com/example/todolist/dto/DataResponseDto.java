package com.example.todolist.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DataResponseDto {

    private Long id;
    private LocalDate dueDate;
    private String title;
    private String description;
    private String priority;
    private int rank;
}
