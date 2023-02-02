package com.project.w3t.service;

import com.project.w3t.exceptions.NotFound404.NotFoundException;
import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserType;
import com.project.w3t.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;
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

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void shouldReturnAllUsers() {
        //given
        List<User> allUsers = new ArrayList<>(Arrays.asList(user));
        when(userRepository.findAll()).thenReturn(allUsers);
        //when
        userService.getAllUsers();
        //then
        verify(userRepository, times(2)).findAll();
    }

    @Test
    void shouldThrowWhenUsersListIsEmpty() {
        Assertions.assertThatThrownBy(() -> userService.getAllUsers())
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Unable to process request - users list does not exist.");
    }

    @Test
    void shouldAddUser() {
//        given user
//        when
        userService.addUser(user);
//        then
        verify(userRepository).save(user);
    }

    @Test
    void shouldDeleteExistingUser() {
        //given
        when(userRepository.existsByUserId(user.getUserId())).thenReturn(true);
        //when
        userService.deleteUser(user.getUserId());
        //then
        verify(userRepository).deleteByUserId(user.getUserId());
    }

    @Test
    void shouldReturnAllUsersByManagerId() {
        //given
        List<User> tempList = new ArrayList<>();
        tempList.add(user);
        when(userRepository.findAllByManagerId(user.getManagerId())).thenReturn(tempList);
        //when
        userService.getAllUsersByManager(user.getManagerId());
        //then
        verify(userRepository, times(2)).findAllByManagerId(user.getManagerId());
    }

    @Test
    void shouldReturnUserByUserId() {
        //given
        when(userRepository.existsByUserId(user.getUserId())).thenReturn(true);
        //when
        userService.getUserByUserId(user.getUserId());
        //then
        verify(userRepository).findByUserId(user.getUserId());
    }
}