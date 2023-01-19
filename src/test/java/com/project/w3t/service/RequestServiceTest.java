package com.project.w3t.service;

import com.project.w3t.exceptions.InvalidCommentLengthException;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.model.Request;
import com.project.w3t.repository.RequestStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    private RequestStorage requestStorage = new RequestStorage();
    private RequestService requestService;

    @BeforeEach
    void setUp() {
        requestService = new RequestService(requestStorage);
    }

    @Test
    void canAddRequest() throws InvalidDateRangeException, InvalidCommentLengthException {
        Request request = new Request();
        int startSize = requestService.getAllRequests().size();
        request.setRequestId(1L);
        LocalDate startDate = LocalDate.of(2022, 2, 1);
        LocalDate endDate = LocalDate.of(2022, 2, 3);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setComment("comment");
        requestService.addRequest(request);

        assertThat(requestService.getAllRequests().size()).isEqualTo(startSize + 1);
    }

    @Test
    void shouldThrowWhenDateRangeIsWrong() throws InvalidCommentLengthException {
        Request request = new Request();
        LocalDate startDate = LocalDate.of(2022, 2, 1);
        LocalDate endDate = LocalDate.of(2022, 2, 3);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setComment("TEST");

        requestService.addRequest(request);

        assertThatThrownBy(() -> requestService.addRequest(request))
                .isInstanceOf(InvalidDateRangeException.class)
                .hasMessageContaining("Invalid date range!");

    }

    @Test
    void shouldThrowWhenCommentIsTooLong() {
        Request request = new Request();
        request.setComment("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor");
        LocalDate startDate = LocalDate.of(2022, 2, 1);
        LocalDate endDate = LocalDate.of(2022, 2, 3);
        request.setStartDate(startDate);
        request.setEndDate(endDate);

        assertThatThrownBy(() -> requestService.addRequest(request))
                .isInstanceOf(InvalidCommentLengthException.class)
                .hasMessageContaining("Your comment is invalid!");

    }

}
