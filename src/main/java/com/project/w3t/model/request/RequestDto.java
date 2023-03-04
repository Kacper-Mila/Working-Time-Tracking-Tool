package com.project.w3t.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.w3t.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestDto {
    @NotBlank
    private String ownerId;
    @Enumerated(EnumType.STRING)
    private RequestType type;
    @NotNull
    private String comment;
    private LocalDate registrationDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate approvalDate;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

}