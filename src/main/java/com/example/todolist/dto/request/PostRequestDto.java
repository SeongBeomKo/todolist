package com.example.todolist.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostRequestDto {

    @NotBlank(message = "할일 제목은 공백일 수 없습니다.")
    private String title;

    @NotBlank(message = "할일 내용은 공백일 수 없습니다.")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate duedate;
}
