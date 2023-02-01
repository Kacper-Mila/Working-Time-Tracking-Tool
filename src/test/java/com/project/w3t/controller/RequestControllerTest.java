package com.project.w3t.controller;

import com.project.w3t.exceptions.BadRequest400.BadRequestException;
import com.project.w3t.service.RequestService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RequestController.class)
@RunWith(SpringRunner.class)
class RequestControllerTest {
    @MockBean
    RequestService requestService;
    @Autowired
    private MockMvc mvc;

    @Test
    void getAllRequestsThrowsException() throws Exception {
        mvc.perform(get("http://localhost:8080/api/v1/requests")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> Assert.assertEquals("Could not find any requests.", result.getResolvedException().getMessage()));

//        when(requestService.getAllRequests())
    }

    @Test
    void addRequestThrowsException() {
    }

    @Test
    void getRequestByIdThrowsException() {
    }
}