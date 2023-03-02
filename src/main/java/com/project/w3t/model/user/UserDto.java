package com.project.w3t.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
        private String email;
        private String userId;
        private String firstName;
        private String lastName;
        private Integer holidays;
        private UserType userType;
        private String managerId;
        private String teamId;
}
