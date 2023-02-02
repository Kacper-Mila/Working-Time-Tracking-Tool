package com.project.w3t.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.w3t.exceptions.BadRequest400.BadRequestException;
import com.project.w3t.exceptions.NotFound404.NotFoundException;
import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserType;
import com.project.w3t.repository.UserRepository;
import com.project.w3t.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static groovy.json.JsonOutput.toJson;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class UserControllerAdviceTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserRepository userRepository;
    private UserService userservice;
    @BeforeEach
    void setUp(){
        userservice = new UserService(userRepository);
    }
    @Test
    void getAllUsersShouldThrowNotFoundException() throws Exception {
        String exceptionParam = "not_found";

        mvc.perform(get("http://localhost:8080/api/v1/users", exceptionParam)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> Assert.assertEquals("Unable to process request - users list does not exist.", result.getResolvedException().getMessage()));
    }

    @Test
    void addUserShouldThrowBadRequestIfMailAlreadyExists() throws Exception {
        String exceptionParam = "bad_request";
        User testUser = new User(1L, "typical@mail.com", "userID", "name", "lastName", 25, UserType.EMPLOYEE, "menagerID", "teamID", null);

        userservice.addUser(testUser);
        User user = new User(1L, "typical@mail.com", "userIDs", "name", "lastName", 25, UserType.EMPLOYEE, "menagerID", "teamID", null);

        mvc.perform(post("http://localhost:8080/api/v1/users", exceptionParam)
                        .content(mapper.writeValueAsString(user).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> Assert.assertEquals("Unable to process request - email address is invalid.", result.getResolvedException().getMessage()));
    }
    @Test
    void addUserShouldThrowBadRequestIfUserIdAlreadyExists() throws Exception{
        String exceptionParam = "bad_request";
        User testUser = new User(1L, "typical@mail.com", "userID", "name", "lastName", 25, UserType.EMPLOYEE, "menagerID", "teamID", null);

        testUser.setEmail("newEmail@mail.com");
        testUser.setUserId("newUserID");
        userservice.addUser(testUser);
        User user = new User(1L, "completlynewemailadress@mail.com", "userID", "name", "lastName", 25, UserType.EMPLOYEE, "menagerID", "teamID", null);

        mvc.perform(post("http://localhost:8080/api/v1/users", exceptionParam)
                        .content(mapper.writeValueAsString(user).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> Assert.assertEquals("Unable to process request - user Id is invalid.", result.getResolvedException().getMessage()));
    }

    @Test
    void updateUser() {
        //TODO Implement.
    }


    @Test
    void deleteUserShouldThrowNotFoundWhenInvalidUserId() throws Exception {
        String exceptionParam = "not_found";
        String userId = "invalidUSerId";

        mvc.perform(delete("http://localhost:8080/api/v1/users/delete", exceptionParam)
                        .param("userId", userId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> Assert.assertEquals("Unable to process request - user does not exist.", result.getResolvedException().getMessage()));
    }

    @Test
    void getAllUsersByManagerShouldThrowNotFoundWhenListEmpty() throws Exception {
        String exceptionParam = "not_found";

        mvc.perform(get("http://localhost:8080/api/v1/users/manager", exceptionParam)
                        .param("managerId", "invalidManagerId")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> Assert.assertEquals("Unable to process request - users list does not exist.", result.getResolvedException().getMessage()));
    }

    @Test
    void getUserByUserIdShouldThrowNotFoundWhenInvalidUserId() throws Exception {
        String exceptionParam = "not_found";

        mvc.perform(get("http://localhost:8080/api/v1/users/userid", exceptionParam)
                        .param("userId", "invalidUserId")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> Assert.assertEquals("Unable to process request - user does not exist.", result.getResolvedException().getMessage()));
    }
}