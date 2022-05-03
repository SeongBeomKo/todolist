package com.example.todolist.dto.request;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UpdateRequestDto {

    @NotBlank(message = "상태는 공백일 수 없습니다.")
    private String status;

    @NotNull(message = "순서는 공백일 수 없습니다.")
    private Integer rank;

    @NotBlank(message = "중요도는 공백일 수 없습니다.")
    private String priority;

    @NotBlank(message = "할일 제목은 공백일 수 없습니다.")
    private String title;

    @NotBlank(message = "할일 내용은 공백일 수 없습니다.")
    private String description;

    //바뀌는 duedate가 아니라 현재 duedate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate duedate;
}
