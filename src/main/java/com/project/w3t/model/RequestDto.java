package com.project.w3t.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private Type type;
    private String comment;

    public RequestDto(LocalDate startDate, LocalDate endDate, Type type, String comment) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.comment = comment;
    }
}
