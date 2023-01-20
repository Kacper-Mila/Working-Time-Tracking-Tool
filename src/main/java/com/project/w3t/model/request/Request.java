package com.project.w3t.model.request;

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
    private RequestType type;
    private String comment;
    private LocalDate registrationDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate approvalDate;
    private RequestStatus status;

    public Request() {
        this.requestId = ID++;
        this.registrationDate = LocalDate.now();
        this.status = RequestStatus.PENDING;
    }

    public List<LocalDate> getRequestDateRange() {
        return RequestDateRange.getDateRange(getStartDate(), getEndDate());
    }
}
