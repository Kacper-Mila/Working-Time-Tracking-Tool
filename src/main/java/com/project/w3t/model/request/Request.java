package com.project.w3t.model.request;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "requests")
//@DynamicInsert
//@DynamicUpdate
public class Request {

    @Id
    @GeneratedValue
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
        this.registrationDate = LocalDate.now();
        this.status = RequestStatus.PENDING;
    }

    public List<LocalDate> getRequestDateRange() {
        return RequestDateRange.getDateRange(getStartDate(), getEndDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Request request = (Request) o;
        return requestId != null && Objects.equals(requestId, request.requestId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
