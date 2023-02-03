package com.project.w3t.service;

import com.project.w3t.exceptions.BadRequest400.BadRequestException;
import com.project.w3t.exceptions.NotFound404.NotFoundException;
import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserDto;
import com.project.w3t.model.user.UserType;
import com.project.w3t.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
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
            "UNKNOWN",
            null);

    private final UserDto userDto = new UserDto(
            UserType.MANAGER,
            "MANAGER4321",
            "NONE");

    private final String userId = user.getUserId();

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
    void shouldThrowNotFoundExceptionWhenUsersListIsEmpty() {
        assertThatThrownBy(() -> userService.getAllUsers())
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
    void shouldThrowBadRequestExceptionWhenEmailAlreadyExistsForAddingUser() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        assertThatThrownBy(() -> userService.addUser(user))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Unable to process request - email address is invalid.");
    }

    @Test
    void shouldThrowBadRequestExceptionWhenUserIdAlreadyExistsForAddingUser() {
        when(userRepository.existsByUserId(user.getUserId())).thenReturn(true);
        assertThatThrownBy(() -> userService.addUser(user))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Unable to process request - user Id is invalid.");
    }

    @Test
    void shouldUpdateUser() {
        //given
        when(userRepository.existsByUserId(userId)).thenReturn(true);
        when(userRepository.findByUserId(userId)).thenReturn(user);

        //when
        userService.updateUser(userId, userDto);
        //then
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowBadRequestExceptionWhenUserTypeIsNullForUpdatingUser() {
        //given
        UserDto userDtoWithNullUserType = new UserDto(null, "MANAGER4321", "NONE");
        when(userRepository.existsByUserId(userId)).thenReturn(true);
        when(userRepository.findByUserId(userId)).thenReturn(user);
        //when //then
        assertThatThrownBy(() -> userService.updateUser(userId, userDtoWithNullUserType))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid user type.");
    }

    @Test
    void shouldThrowBadRequestExceptionWhenManagerIdIsNullForUpdatingUser() {
        //given
        UserDto userDtoWithNullManagerId = new UserDto(UserType.EMPLOYEE, null, "NONE");
        when(userRepository.existsByUserId(userId)).thenReturn(true);
        when(userRepository.findByUserId(userId)).thenReturn(user);
        //when //then
        assertThatThrownBy(() -> userService.updateUser(userId, userDtoWithNullManagerId))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Manager id is missing");
    }

    @Test
    void shouldThrowBadRequestExceptionWhenManagerIdIsEmptyForUpdatingUser() {
        //given
        UserDto userDtoWithEmptyManagerId = new UserDto(UserType.EMPLOYEE, "", "NONE");
        when(userRepository.existsByUserId(userId)).thenReturn(true);
        when(userRepository.findByUserId(userId)).thenReturn(user);
        //when //then
        assertThatThrownBy(() -> userService.updateUser(userId, userDtoWithEmptyManagerId))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Manager id is missing");
    }

    @Test
    void shouldThrowBadRequestExceptionWhenTeamIdIsNullForUpdatingUser() {
        //given
        UserDto userDtoWithNullTeamId = new UserDto(UserType.EMPLOYEE, "MANAGER4321", null);
        when(userRepository.existsByUserId(userId)).thenReturn(true);
        when(userRepository.findByUserId(userId)).thenReturn(user);
        //when //then
        assertThatThrownBy(() -> userService.updateUser(userId, userDtoWithNullTeamId))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Team id is missing");
    }

    @Test
    void shouldThrowBadRequestExceptionWhenTeamIdIsEmptyForUpdatingUser() {
        //given
        UserDto userDtoWithEmptyTeamId = new UserDto(UserType.EMPLOYEE, "MANAGER4321", "");
        when(userRepository.existsByUserId(userId)).thenReturn(true);
        when(userRepository.findByUserId(userId)).thenReturn(user);
        //when //then
        assertThatThrownBy(() -> userService.updateUser(userId, userDtoWithEmptyTeamId))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Team id is missing");
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
    void shouldThrowNotFoundExceptionWhenUserIdIsNotExistForDeletingUser() {
        //given
        when(userRepository.existsByUserId(user.getUserId())).thenReturn(false);
        //when //then
        assertThatThrownBy(() -> userService.deleteUser(userId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Unable to process request - user does not exist.");
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
    void shouldThrowNotFoundExceptionWhenUsersListForManagerIsEmpty() {
        //given
        List<User> tempList = new ArrayList<>();
        when(userRepository.findAllByManagerId(user.getManagerId())).thenReturn(tempList);
        //when //then
        assertThatThrownBy(() -> userService.getAllUsersByManager(user.getManagerId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Unable to process request - users list does not exist.");
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

    @Test
    void shouldThrowNotFoundExceptionWhenUserIdIsNotExistForGettingUserByUserId() {
        //given
        when(userRepository.existsByUserId(user.getUserId())).thenReturn(false);
        //when //then
        assertThatThrownBy(() -> userService.getUserByUserId(userId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Unable to process request - user does not exist.");
    }
}