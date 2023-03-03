package com.project.w3t.security.auth;

import com.project.w3t.model.user.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String userId;
    private String password;
    private String firstName;
    private String lastName;
    private Integer holidays;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private String managerId;
    private String teamId;
}