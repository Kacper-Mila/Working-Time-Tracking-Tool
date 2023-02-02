package com.project.w3t.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestDto {
    @NotBlank
    private LocalDate startDate;
    @NotBlank
    private LocalDate endDate;
    @NotBlank
    private RequestType type;
    @NotBlank
    private String comment;

    public RequestDto(LocalDate startDate, LocalDate endDate, RequestType type, String comment) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.comment = comment;
    }
}
