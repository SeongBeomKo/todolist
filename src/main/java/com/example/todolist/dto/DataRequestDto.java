package com.example.todolist.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class DataRequestDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate duedate;
}
