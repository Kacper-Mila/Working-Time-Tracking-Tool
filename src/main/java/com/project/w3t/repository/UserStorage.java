package com.project.w3t.repository;

import com.project.w3t.model.Request;
import com.project.w3t.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserStorage implements UserRepository {

    private List<User> userList = new ArrayList<>();


}
