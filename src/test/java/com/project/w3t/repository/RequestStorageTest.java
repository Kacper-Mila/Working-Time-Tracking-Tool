package com.project.w3t.repository;

import com.project.w3t.exceptions.InvalidCommentLengthException;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.Request;
import com.project.w3t.model.RequestDto;
import com.project.w3t.model.Status;
import com.project.w3t.model.Type;
import com.project.w3t.service.RequestService;
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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RequestStorageTest {

    private List<Request> userRequestList;
    private static final int COMMENT_MAX_LENGTH = 250;
    RequestStorage requestStorage = new RequestStorage();

    private Request request = new Request(1L, "123", Type.HOLIDAY,
            "comment", LocalDate.now(),LocalDate.of(2022, 2, 1),
            LocalDate.of(2022, 2, 3),LocalDate.of(2022, 2, 10),
            Status.PENDING );

    @BeforeEach
    void setUp() {
        userRequestList = new ArrayList<>();
    }

    @Test
    void canAddRequest() throws InvalidDateRangeException, InvalidCommentLengthException {
        int startSize = requestStorage.getAllRequests().size();
        requestStorage.addRequest(request);

        assertThat(requestStorage.getAllRequests().size()).isEqualTo(startSize + 1);
    }

    @Test
    void shouldThrowWhenDateRangeIsWrong() throws InvalidCommentLengthException {
        requestStorage.addRequest(request);

        assertThatThrownBy(() -> requestStorage.addRequest(request))
                .isInstanceOf(InvalidDateRangeException.class)
                .hasMessageContaining("Invalid date range!");

    }

    @Test
    void shouldThrowWhenCommentIsTooLong() {
        request.setComment("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor");

        assertThatThrownBy(() -> requestStorage.addRequest(request))
                .isInstanceOf(InvalidCommentLengthException.class)
                .hasMessageContaining("Your comment is invalid!");

    }

    @Test
    void shouldDeleteRequest() throws InvalidCommentLengthException, InvalidRequestIdException {
        requestStorage.addRequest(request);
        int requestsAmount = requestStorage.getAllRequests().size();
        requestStorage.deleteRequest(1L);

        assertThat(requestStorage.getAllRequests().size()).isEqualTo(requestsAmount - 1);
    }

    @Test
    void shouldThrowInvalidRequestIdExceptionWhenInvalidRequestIdForDeleteRequestMethod() throws InvalidCommentLengthException {
        requestStorage.addRequest(request);

        assertThatThrownBy(() -> requestStorage.deleteRequest(2L))
                .isInstanceOf(InvalidRequestIdException.class)
                .hasMessageContaining("Invalid request Id!");
    }

    @Test
    void shouldReturnAllRequestsByOvertimeType() throws InvalidCommentLengthException {
        List<Request> expected = new ArrayList<>();
        Request request2 = new Request(5L, "1233", Type.OVERTIME,
                "comment", LocalDate.now(),LocalDate.of(2023, 2, 1),
                LocalDate.of(2023, 2, 3),LocalDate.of(2023, 2, 10),
                Status.PENDING );

        requestStorage.addRequest(request);
        requestStorage.addRequest(request2);
        expected.add(request2);


        assertThat(requestStorage.getAllRequestsByType("overtime")).isEqualTo(expected);
    }

    @Test
    void shouldReturnAllRequestsByRemoteType() throws InvalidCommentLengthException {
        List<Request> expected = new ArrayList<>();
        Request request2 = new Request(5L, "1233", Type.REMOTE,
                "comment", LocalDate.now(),LocalDate.of(2023, 2, 1),
                LocalDate.of(2023, 2, 3),LocalDate.of(2023, 2, 10),
                Status.PENDING );

        requestStorage.addRequest(request);
        requestStorage.addRequest(request2);
        expected.add(request2);


        assertThat(requestStorage.getAllRequestsByType("remote")).isEqualTo(expected);
    }

    @Test
    void shouldReturnAllRequestsByHolidayType() throws InvalidCommentLengthException {
        List<Request> expected = new ArrayList<>();
        Request request2 = new Request(5L, "1233", Type.HOLIDAY,
                "comment", LocalDate.now(),LocalDate.of(2023, 2, 1),
                LocalDate.of(2023, 2, 3),LocalDate.of(2023, 2, 10),
                Status.PENDING );

        requestStorage.addRequest(request);
        requestStorage.addRequest(request2);
        expected.add(request);
        expected.add(request2);


        assertThat(requestStorage.getAllRequestsByType("holiday")).isEqualTo(expected);
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

        requestStorage.addRequest(request);
        expected.add(request);
        expected.add(request2);

        assertThat(requestStorage.getAllRequestsByType("holiday"))
                .doesNotHaveSameHashCodeAs(expected);
    }

    @Test
    void shouldThrowExceptionWhenRequestTypeDoesNotMatch() {
        assertThatThrownBy(() -> requestStorage.getAllRequestsByType("hollidaaay"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldThrowExceptionWhenRequestTypeIsEmpty() {
        assertThatThrownBy(() -> requestStorage.getAllRequestsByType(""))
                .isInstanceOf(NullPointerException.class);
    }

}