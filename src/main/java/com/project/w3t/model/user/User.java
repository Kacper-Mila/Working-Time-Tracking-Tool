package com.project.w3t.model.user;

import com.project.w3t.model.request.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor

public class User {

    private static Long ID = 1L;
    private Long id;
    private String email;
    private String userId;
    private String firstName;
    private String lastName;
    private Integer holidays;
    private UserType userType;
    private String managerId;
    private String teamId;

    public User() {
        this.id = ID++;
        this.userType = UserType.EMPLOYEE;
    }
}
