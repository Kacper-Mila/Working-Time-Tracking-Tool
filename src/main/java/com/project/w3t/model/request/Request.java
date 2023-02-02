package com.project.w3t.model.request;

import com.project.w3t.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "requests")
//@DynamicInsert
//@DynamicUpdate
public class Request {

    @Id
    @GeneratedValue
    private Long id;
    private String ownerId;
    @Enumerated(EnumType.STRING)
    private RequestType type;
    private String comment;
    private LocalDate registrationDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate approvalDate;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

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
        return id != null && Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
