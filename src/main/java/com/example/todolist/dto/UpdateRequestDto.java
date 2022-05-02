package com.example.todolist.dto;


import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UpdateRequestDto {

    //data input 전부 not null
    private String status;
    private Integer rank;
    private String priority;
    private String title;
    private String description;
    //바뀌는 duedate가 아니라 현재 duedate
    private LocalDate duedate;
}
