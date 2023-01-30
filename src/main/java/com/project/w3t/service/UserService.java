package com.project.w3t.service;

import com.project.w3t.exceptions.*;
import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserDto;
import com.project.w3t.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    public void addUser(User user) {
//        try {
//            userRepository.addUser(user);
//        } catch (InvalidUserIdException e) {
//            System.out.println("Invalid user id.");
//        } catch (InvalidEmailException e) {
//            System.out.println("Invalid email address.");
//        }
//    }
//
//    public void updateUser(String userId, UserDto userDto) {
//        userRepository.updateUser(userId, userDto);
//    }
//
//    public void deleteUser(String userId) {
//        try {
//            userRepository.deleteUser(userId);
//        } catch (UserNotFoundException e) {
//            System.out.println("User not found.");
//        }
//    }
//
//    public List<User> getAllUsersByManager(String managerId) {
//        return userRepository.getAllUsersByManager(managerId);
//    }
//
//    public Optional<User> getUserByUserId(String userId) {
//        try {
//            return Optional.ofNullable(userRepository.getUserByUserId(userId));
//        } catch (UserNotFoundException e) {
//            System.out.println("User not found.");
//            return Optional.empty();
//        }
//    }
}
