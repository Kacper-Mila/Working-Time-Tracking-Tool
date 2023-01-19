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
        return userRepository.getAllUsers();
    }

    public void addUser(User user) {
        userRepository.addUser(user);
    }

    public void updateUser(String userId, UserDto userDto) {
        userRepository.updateUser(userId, userDto);
    }

    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
    }

    public List<User> getAllUsersByManager(String managerId) {
        return userRepository.getAllUsersByManager(managerId);
    }

    public Object getUserById(String userId) {
        return userRepository.getUserById(userId);
    }
}
