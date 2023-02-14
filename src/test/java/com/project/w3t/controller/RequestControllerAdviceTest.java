package com.project.w3t.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.w3t.exceptions.BadRequest400.BadRequestException;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestStatus;
import com.project.w3t.model.request.RequestType;
import com.project.w3t.service.RequestService;
import groovy.json.JsonOutput;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static groovy.json.JsonOutput.toJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
class RequestControllerAdviceTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

//    TODO fix dem tests my friend!

    @Test
    void getAllRequestsThrowsException() throws Exception {
        String exceptionParam = "bad_request";
        mvc.perform(get("http://localhost:8080/api/v1/requests", exceptionParam)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> Assert.assertEquals("Could not find any requests.", result.getResolvedException().getMessage()));
    }

    @Test
    void addRequestThrowsCommentTooLongException() throws Exception {
        String exceptionParam = "bad_request";

        Request request = new Request(2L, "123", RequestType.HOLIDAY,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                        "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                        "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                        "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor",
                LocalDate.now(), LocalDate.of(2023, 3, 3),
                LocalDate.of(2023, 3, 10), LocalDate.of(2023, 3, 10),
                RequestStatus.PENDING, null);

        mvc.perform(post("http://localhost:8080/api/v1/requests", exceptionParam)
                        .content(mapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> Assert.assertEquals("Comment is too long.", result.getResolvedException().getMessage()));
    }

    @Test
    void addRequestThrowsInvalidDateRangeException() throws Exception{
        String exceptionParam = "bad_request";

        Request request = new Request(2L, "123", RequestType.HOLIDAY,
                "comment",
                LocalDate.now(), LocalDate.of(2023, 3, 10),
                LocalDate.of(2023, 3, 3), LocalDate.of(2023, 3, 10),
                RequestStatus.PENDING, null);

        mvc.perform(post("http://localhost:8080/api/v1/requests", exceptionParam)
                        .content(toJson(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> Assert.assertEquals("Invalid date range.", result.getResolvedException().getMessage()));
    }


    @Test
    void getRequestByIdThrowsException() throws Exception {
        String exceptionParam = "bad_request";
        mvc.perform(get("http://localhost:8080/api/v1/requests/id?requestId=2137", exceptionParam)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> Assert.assertEquals("Request with this id does not exists.", result.getResolvedException().getMessage()));
    }
}