package com.project.w3t.repository;

import com.project.w3t.exceptions.InvalidCommentLengthException;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestStatus;
import com.project.w3t.model.request.RequestType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class RequestStorageTest {
    RequestStorage requestStorage = new RequestStorage();
    private List<Request> userRequestList;
    private static final int COMMENT_MAX_LENGTH = 250;

    private final Request request1 = new Request(1L, "123", RequestType.HOLIDAY,
            "comment", LocalDate.now(),LocalDate.of(2022, 2, 1),
            LocalDate.of(2022, 2, 3),LocalDate.of(2022, 2, 10),
            RequestStatus.PENDING );

    private final Request request2 = new Request(5L, "1233", RequestType.OVERTIME,
            "comment", LocalDate.now(),LocalDate.of(2023, 2, 1),
            LocalDate.of(2023, 2, 3),LocalDate.of(2023, 2, 10),
            RequestStatus.PENDING );

    private final Request request3 = new Request(21L, "1233", RequestType.REMOTE,
            "comment", LocalDate.now(),LocalDate.of(2023, 2, 1),
            LocalDate.of(2023, 2, 3),LocalDate.of(2023, 2, 10),
            RequestStatus.PENDING );
    @BeforeEach
    void setUp() throws InvalidCommentLengthException {
        userRequestList = new ArrayList<>();
        //TODO add requests to list in here
        requestStorage.addRequest(request1);
        requestStorage.addRequest(request2);
        requestStorage.addRequest(request3);
    }

    @Test
    void canAddRequest() throws InvalidDateRangeException, InvalidCommentLengthException {
        int startSize = requestStorage.getAllRequests().size();
        requestStorage.addRequest(request1);

        assertThat(requestStorage.getAllRequests().size()).isEqualTo(startSize + 1);
    }

    @Test
    void shouldThrowWhenDateRangeIsWrong() throws InvalidCommentLengthException {
        requestStorage.addRequest(request1);

        assertThatThrownBy(() -> requestStorage.addRequest(request1))
                .isInstanceOf(InvalidDateRangeException.class)
                .hasMessageContaining("Invalid date range!");

    }

    @Test
    void shouldThrowWhenCommentIsTooLong() {
        request1.setComment("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor");

        assertThatThrownBy(() -> requestStorage.addRequest(request1))
                .isInstanceOf(InvalidCommentLengthException.class)
                .hasMessageContaining("Your comment is invalid!");

    }

    @Test
    void shouldDeleteRequest() throws InvalidCommentLengthException, InvalidRequestIdException {
        requestStorage.addRequest(request1);
        int requestsAmount = requestStorage.getAllRequests().size();
        requestStorage.deleteRequest(1L);

        assertThat(requestStorage.getAllRequests().size()).isEqualTo(requestsAmount - 1);
    }

    @Test
    void shouldThrowInvalidRequestIdExceptionWhenInvalidRequestIdForDeleteRequestMethod() throws InvalidCommentLengthException {
        requestStorage.addRequest(request1);

        assertThatThrownBy(() -> requestStorage.deleteRequest(2L))
                .isInstanceOf(InvalidRequestIdException.class)
                .hasMessageContaining("Invalid request Id!");
    }

    @Test
    void shouldReturnAllRequestsByOvertimeType() {
        //TODO Use created list in BeforeEach, add requests in BeforeEach

        List<Request> expected = new ArrayList<>();
        expected.add(request2);

        assertThat(requestStorage.getAllRequestsByType("overtime")).isEqualTo(expected);
    }

    @Test
    void shouldReturnAllRequestsByRemoteType() {
        //TODO Use created list in BeforeEach, add requests in BeforeEach

        List<Request> expected = new ArrayList<>();

        expected.add(request3);


        assertThat(requestStorage.getAllRequestsByType("remote")).isEqualTo(expected);
    }

    @Test
    void shouldReturnAllRequestsByHolidayType() {
        //TODO Use created list in BeforeEach, add requests in BeforeEach

        List<Request> expected = new ArrayList<>();
        expected.add(request1);

        assertThat(requestStorage.getAllRequestsByType("holiday")).isEqualTo(expected);
    }


    // MA TO SENS? MA TO SENS? MA TO SENS???
    // MA TO SENS? MA TO SENS? MA TO SENS???
    // MA TO SENS? MA TO SENS? MA TO SENS???
    // MA TO SENS? MA TO SENS? MA TO SENS???
    @Test
    void shouldReturnAllRequestsByTypeNegative() {
        //TODO Use created list in BeforeEach, add requests in BeforeEach

        List<Request> expected = new ArrayList<>();

        expected.add(request1);
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
    //ID TESTS
    @Test
    void shouldReturnRequestById() throws InvalidRequestIdException {
        assertThat(requestStorage.getRequestById(21L)).isEqualTo(request3);
    }
    @Test
    void shouldThrowInvalidRequestIdException(){
        assertThatThrownBy(() -> requestStorage.getRequestById(2137L)).isInstanceOf(Exception.class);
    }
}