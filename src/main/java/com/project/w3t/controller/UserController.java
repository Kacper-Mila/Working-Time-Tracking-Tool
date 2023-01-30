package com.project.w3t.controller;

import com.project.w3t.exceptions.InvalidEmailException;
import com.project.w3t.exceptions.InvalidUserIdException;
import com.project.w3t.exceptions.UserNotFoundException;
import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserDto;
import com.project.w3t.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public void addUser(@RequestBody User user) throws InvalidEmailException, InvalidUserIdException {
        userService.addUser(user);
    }
//
//    @PatchMapping("/update")
//    public void updateUser(@RequestParam String userId, @RequestBody UserDto userDto) {
//        userService.updateUser(userId, userDto);
//    }
//
    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam String userId) throws UserNotFoundException {
        userService.deleteUser(userId);
    }

    @GetMapping("/manager")
    public List<User> getAllUsersByManager(@RequestParam String managerId) {
        return userService.getAllUsersByManager(managerId);
    }

    @GetMapping("/userid")
    @ResponseBody
    public User getUserByUserId(@RequestParam String userId) throws UserNotFoundException {
        return userService.getUserByUserId(userId);
    }
}
