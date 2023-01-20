package com.project.w3t.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private RequestType type;
    private String comment;
}
