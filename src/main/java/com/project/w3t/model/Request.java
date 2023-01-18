package com.project.w3t.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
//@Entity
public class Request {
    private static Long ID = 1L;
    private Long requestId;
    private String ownerId;
    private Type type;
    private String comment;
    private LocalDate registrationDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate approvalDate;
    private Status status;

    public Request() {
        this.requestId = ID++;
        this.registrationDate = LocalDate.now();
        this.status = Status.PENDING;
    }

    public List<LocalDate> getRequestDateRange() {
        return DateRange.getDateRange(getStartDate(), getEndDate());
    }
}
