package com.project.w3t.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@ToString
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
    private Integer holidays;
    private UserType userType;
    private String managerId;
    private String teamId;

    public User() {
        this.userType = UserType.EMPLOYEE;
    }
}
