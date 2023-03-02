package com.project.w3t.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.w3t.model.request.Request;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users")
//@DynamicInsert
//@DynamicUpdate
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String userId;
    private String firstName;
    private String lastName;
    private String password;
    private Integer holidays;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private String managerId;
    private String teamId;
    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Request> requestList;

    public User() {
        this.userType = UserType.EMPLOYEE;
    }
}