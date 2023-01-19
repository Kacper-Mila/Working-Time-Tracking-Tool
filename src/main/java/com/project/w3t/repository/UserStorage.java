package com.project.w3t.repository;

import com.project.w3t.model.Request;
import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserDto;
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


    @Override
    public List<User> getAllUsers() {
        return userList;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void updateUser(String userId, UserDto userDto) {

    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public List<User> getAllUsersByManager(String managerId) {
        return null;
    }

    @Override
    public Object getUserById(String userId) {
        return null;
    }
}
