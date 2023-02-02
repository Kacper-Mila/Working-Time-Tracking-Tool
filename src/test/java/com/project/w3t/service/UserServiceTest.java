package com.project.w3t.service;

import com.project.w3t.exceptions.BadRequest400.BadRequestException;
import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserType;
import com.project.w3t.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
            "UNKNOWN");

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void shouldThrowBadRequestException() {
//        given
//        when
//        then
        assertThatThrownBy(() -> userService.addUser(null))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Unable to process request - user data is invalid.");
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
        when(userRepository.existsByManagerId(user.getManagerId())).thenReturn(true);
        //when
        userService.getAllUsersByManager(user.getManagerId());
        //then
        verify(userRepository).findAllByManagerId(user.getManagerId());
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