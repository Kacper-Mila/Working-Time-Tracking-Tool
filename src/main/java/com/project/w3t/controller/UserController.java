package com.project.w3t.controller;

import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserDto;
import com.project.w3t.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
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
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @PatchMapping("/update")
    public void updateUser(@RequestParam String userId, @RequestBody UserDto userDto) {
        userService.updateUser(userId, userDto);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam String userId){
        userService.deleteUser(userId);
    }

    @GetMapping("/manager")
    public List<User> getAllUsersByManager(@RequestParam String managerId) {
        return userService.getAllUsersByManager(managerId);
    }

    @GetMapping("/userId")
    @ResponseBody
    public User getUserByUserId(@RequestParam String userId){
        return userService.getUserByUserId(userId);
    }
}