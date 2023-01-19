package com.project.w3t.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private Type type;
    private String comment;
}
