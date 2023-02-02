package com.project.w3t.controller;

import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserDto;
import com.project.w3t.model.user.UserType;
import com.project.w3t.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    UserController userController;

    private final User user = new User(
            1L,
            "email@gmail.com",
            "USER1234",
            "Jan",
            "Kochanowski",
            10,
            UserType.EMPLOYEE,
            "MANAGER1234",
            "UNKNOWN",
            null);

    private final UserDto userDto = new UserDto(
            1L,
            "mailon@gmail.com",
            "USER4321",
            "Janek",
            "Kochanek",
            15,
            UserType.MANAGER,
            "MANAGER4321",
            "NONE");

    @BeforeEach
    void setUp() {
        userController = new UserController(userService);
    }

    @Test
    public void shouldReturnAllUsers() {
        userController.getAllUsers();
        verify(userService).getAllUsers();
    }

    @Test
    public void shouldAddNewUser() {
        userController.addUser(user);
        verify(userService).addUser(user);
    }

    @Test
    public void shouldUpdateExistingUser() {
        userController.updateUser(userDto.getUserId(), userDto);
        verify(userService).updateUser(userDto.getUserId(), userDto);
    }

    @Test
    public void shouldDeleteExistingUser() {
        userController.deleteUser(user.getUserId());
        verify(userService).deleteUser(user.getUserId());
    }

    @Test
    public void shouldReturnAllUsersWithGivenManagerId() {
        userController.getAllUsersByManager(user.getManagerId());
        verify(userService).getAllUsersByManager(user.getManagerId());
    }

    @Test
    public void shouldReturnUserById() {
        userController.getUserByUserId(user.getUserId());
        verify(userService).getUserByUserId(user.getUserId());
    }
}