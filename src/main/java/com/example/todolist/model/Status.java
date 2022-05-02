package com.example.todolist.model;

public enum Status {

    ONGOING("진행중"), COMPLETED("완료"), CANCELLED("취소");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.getStatus();
    }
}
