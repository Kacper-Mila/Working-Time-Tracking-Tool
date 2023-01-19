package com.project.w3t.model.user;

import lombok.Data;

@Data
public class UserDto {
        private Long id;
        private String email;
        private String userId;
        private String firstName;
        private String lastName;
        private Integer holidays;
        private UserType userType;
        private String managerId;
        private String teamId;
}
