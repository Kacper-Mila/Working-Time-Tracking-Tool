package com.project.w3t.model.user;

import com.project.w3t.model.request.Request;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private String managerId;
    private String teamId;
//    @OneToMany(mappedBy = "user")
//    private List<Request> requestList;

    public User() {
        this.userType = UserType.EMPLOYEE;
    }
}
