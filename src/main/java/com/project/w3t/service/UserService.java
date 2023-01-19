package com.project.w3t.service;

import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserDto;
import com.project.w3t.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return null;
    }

    public void addUser(User user) {

    }

    public void updateUser(String userId, UserDto userDto) {

    }

    public void deleteUser(String userId) {

    }

    public List<User> getAllUsersByManager(String managerId) {
        return null;
    }

    public Object getUserById(String userId) {
    return null;
    }
}
