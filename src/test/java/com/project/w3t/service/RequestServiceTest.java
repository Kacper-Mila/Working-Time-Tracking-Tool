package com.project.w3t.service;

import com.project.w3t.exceptions.InvalidCommentLengthException;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.Request;
import com.project.w3t.model.Status;
import com.project.w3t.model.Type;
import com.project.w3t.repository.RequestStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    private RequestStorage requestStorage = new RequestStorage();
    private RequestService requestService;

    private Request request = new Request(1L, "123", Type.HOLIDAY,
            "comment", LocalDate.now(),LocalDate.of(2022, 2, 1),
            LocalDate.of(2022, 2, 3),LocalDate.of(2022, 2, 10),
            Status.PENDING );

    @BeforeEach
    void setUp() {
        requestService = new RequestService(requestStorage);
    }

    @Test
    void canAddRequest() throws InvalidDateRangeException, InvalidCommentLengthException {
        int startSize = requestService.getAllRequests().size();
        requestService.addRequest(request);

        assertThat(requestService.getAllRequests().size()).isEqualTo(startSize + 1);
    }

    @Test
    void shouldThrowWhenDateRangeIsWrong() throws InvalidCommentLengthException {
        requestService.addRequest(request);

        assertThatThrownBy(() -> requestService.addRequest(request))
                .isInstanceOf(InvalidDateRangeException.class)
                .hasMessageContaining("Invalid date range!");

    }

    @Test
    void shouldThrowWhenCommentIsTooLong() {
        request.setComment("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor");

        assertThatThrownBy(() -> requestService.addRequest(request))
                .isInstanceOf(InvalidCommentLengthException.class)
                .hasMessageContaining("Your comment is invalid!");

    }

    @Test
    void shouldDeleteRequest() throws InvalidCommentLengthException, InvalidRequestIdException {
        requestService.addRequest(request);
        int requestsAmount = requestService.getAllRequests().size();
        requestService.deleteRequest(1L);

        assertThat(requestService.getAllRequests().size()).isEqualTo(requestsAmount - 1);
    }

    @Test
    void shouldThrowInvalidRequestIdExceptionWhenInvalidRequestIdForDeleteRequestMethod() throws InvalidCommentLengthException {
        requestService.addRequest(request);

        assertThatThrownBy(() -> requestService.deleteRequest(2L))
                .isInstanceOf(InvalidRequestIdException.class)
                .hasMessageContaining("Invalid request Id!");
    }

    @Test
    void shouldReturnAllRequestsByType() throws InvalidCommentLengthException {
        List<Request> expected = new ArrayList<>();
        Request request2 = new Request(5L, "1233", Type.HOLIDAY,
                "comment", LocalDate.now(),LocalDate.of(2023, 2, 1),
                LocalDate.of(2023, 2, 3),LocalDate.of(2023, 2, 10),
                Status.PENDING );

        requestService.addRequest(request);
        requestService.addRequest(request2);
        expected.add(request);
        expected.add(request2);

        assertThat(requestService.getAllRequestsByType("holiday"))
                .hasValue(expected);
    }


    // MA TO SENS? MA TO SENS? MA TO SENS???
    // MA TO SENS? MA TO SENS? MA TO SENS???
    // MA TO SENS? MA TO SENS? MA TO SENS???
    // MA TO SENS? MA TO SENS? MA TO SENS???
    @Test
    void shouldReturnAllRequestsByTypeNegative() throws InvalidCommentLengthException {
        List<Request> expected = new ArrayList<>();
        Request request2 = new Request(5L, "1233", Type.HOLIDAY,
                "comment", LocalDate.now(),LocalDate.of(2023, 2, 1),
                LocalDate.of(2023, 2, 3),LocalDate.of(2023, 2, 10),
                Status.PENDING );

        requestService.addRequest(request);
        expected.add(request);
        expected.add(request2);

        assertThat(requestService.getAllRequestsByType("holiday"))
                .doesNotHaveSameHashCodeAs(expected);
    }

    @Test
    void shouldThrowExceptionWhenRequestTypeDoesNotMatch() {
        assertThatThrownBy(() -> requestService.getAllRequestsByType("hollidaaay"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldThrowExceptionWhenRequestTypeIsEmpty() {
        assertThatThrownBy(() -> requestService.getAllRequestsByType(""))
                .isInstanceOf(NullPointerException.class);
    }

}
