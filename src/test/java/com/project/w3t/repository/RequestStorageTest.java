package com.project.w3t.repository;

import com.project.w3t.exceptions.InvalidCommentLengthException;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestDto;
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

    private List<Request> userRequestList;
    RequestStorage requestStorage = new RequestStorage();

    private final Request request = new Request(1L, "123", RequestType.HOLIDAY,
            "comment", LocalDate.now(),LocalDate.of(2022, 2, 1),
            LocalDate.of(2022, 2, 3),LocalDate.of(2022, 2, 10),
            RequestStatus.PENDING );

    private final RequestDto requestDto = new RequestDto(LocalDate.of(2022, 3, 1),
            LocalDate.of(2022, 3, 3), RequestType.OVERTIME, "comment");

    @BeforeEach
    void setUp() {
        userRequestList = new ArrayList<>();
    }

    @Test
    void shouldAddRequest() throws InvalidDateRangeException, InvalidCommentLengthException {
        int startSize = requestStorage.getAllRequests().size();
        requestStorage.addRequest(request);

        assertThat(requestStorage.getAllRequests().size()).isEqualTo(startSize + 1);
    }

    @Test
    void shouldThrowWhenDateRangeIsWrongForAddMethod() throws InvalidCommentLengthException {
        requestStorage.addRequest(request);
        Request request1 = new Request(2L, "123", RequestType.HOLIDAY,
                "comment", LocalDate.now(),LocalDate.of(2022, 2, 1),
                LocalDate.of(2022, 2, 3),LocalDate.of(2022, 2, 10),
                RequestStatus.PENDING );

        assertThatThrownBy(() -> requestStorage.addRequest(request1))
                .isInstanceOf(InvalidDateRangeException.class)
                .hasMessageContaining("Invalid date range!");

    }

    @Test
    void shouldThrowWhenCommentIsTooLongForAddMethod() {
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
        Request request2 = new Request(5L, "1233", RequestType.OVERTIME,
                "comment", LocalDate.now(),LocalDate.of(2023, 2, 1),
                LocalDate.of(2023, 2, 3),LocalDate.of(2023, 2, 10),
                RequestStatus.PENDING );

        requestStorage.addRequest(request);
        requestStorage.addRequest(request2);
        expected.add(request2);


        assertThat(requestStorage.getAllRequestsByType("overtime")).isEqualTo(expected);
    }

    @Test
    void shouldReturnAllRequestsByRemoteType() throws InvalidCommentLengthException {
        List<Request> expected = new ArrayList<>();
        Request request2 = new Request(5L, "1233", RequestType.REMOTE,
                "comment", LocalDate.now(),LocalDate.of(2023, 2, 1),
                LocalDate.of(2023, 2, 3),LocalDate.of(2023, 2, 10),
                RequestStatus.PENDING );

        requestStorage.addRequest(request);
        requestStorage.addRequest(request2);
        expected.add(request2);


        assertThat(requestStorage.getAllRequestsByType("remote")).isEqualTo(expected);
    }

    @Test
    void shouldReturnAllRequestsByHolidayType() throws InvalidCommentLengthException {
        List<Request> expected = new ArrayList<>();
        Request request2 = new Request(5L, "1233", RequestType.HOLIDAY,
                "comment", LocalDate.now(),LocalDate.of(2023, 2, 1),
                LocalDate.of(2023, 2, 3),LocalDate.of(2023, 2, 10),
                RequestStatus.PENDING );

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
        Request request2 = new Request(5L, "1233", RequestType.HOLIDAY,
                "comment", LocalDate.now(),LocalDate.of(2023, 2, 1),
                LocalDate.of(2023, 2, 3),LocalDate.of(2023, 2, 10),
                RequestStatus.PENDING );

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

    @Test
    void shouldUpdateRequestType() throws InvalidCommentLengthException, InvalidRequestIdException {
        Long id = 1L;
        requestStorage.addRequest(request);
        requestStorage.updateRequest(id, requestDto);

        RequestType expectedRequestType = RequestType.OVERTIME;

        assertThat(requestStorage.getRequestById(id).getType()).isEqualTo(expectedRequestType);
    }

    @Test
    void shouldUpdateRequestStartDate() throws InvalidCommentLengthException, InvalidRequestIdException {
        Long id = 1L;
        requestStorage.addRequest(request);
        requestStorage.updateRequest(id, requestDto);

        LocalDate expectedStartDate = requestDto.getStartDate();

        assertThat(requestStorage.getRequestById(id).getStartDate()).isEqualTo(expectedStartDate);
    }

    @Test
    void shouldUpdateRequestEndDate() throws InvalidCommentLengthException, InvalidRequestIdException {
        Long id = 1L;
        requestStorage.addRequest(request);
        requestStorage.updateRequest(id, requestDto);

        LocalDate expectedEndDate = requestDto.getEndDate();

        assertThat(requestStorage.getRequestById(id).getEndDate()).isEqualTo(expectedEndDate);
    }

    @Test
    void shouldUpdateRequestComment() throws InvalidCommentLengthException, InvalidRequestIdException {
        Long id = 1L;
        requestStorage.addRequest(request);
        requestStorage.updateRequest(id, requestDto);

        String expectedComment = requestDto.getComment();

        assertThat(requestStorage.getRequestById(id).getComment()).isEqualTo(expectedComment);
    }

    @Test
    void shouldUpdateRequestStatusToPending() throws InvalidCommentLengthException, InvalidRequestIdException {
        Long id = 1L;
        requestStorage.addRequest(request);
        requestStorage.updateRequest(id, requestDto);

        RequestStatus expectedStatus = RequestStatus.PENDING;

        assertThat(requestStorage.getRequestById(id).getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void shouldThrowWhenCommentIsTooLongForUpdateMethod() throws InvalidCommentLengthException {
        requestStorage.addRequest(request);
        requestDto.setComment("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor");

        assertThatThrownBy(() -> requestStorage.updateRequest(1L, requestDto))
                .isInstanceOf(InvalidCommentLengthException.class)
                .hasMessageContaining("Your comment is invalid!");
    }

    @Test
    void shouldThrowWhenDateRangeIsWrongForUpdateMethod() throws InvalidCommentLengthException {
        requestStorage.addRequest(request);
        Request request2 = new Request(5L, "1233", RequestType.HOLIDAY,
                "comment", LocalDate.now(),LocalDate.of(2023, 2, 1),
                LocalDate.of(2023, 2, 3),LocalDate.of(2023, 2, 10),
                RequestStatus.PENDING );
        requestStorage.addRequest(request2);
        requestDto.setStartDate(request2.getStartDate());
        requestDto.setEndDate(request2.getEndDate());

        assertThatThrownBy(() -> requestStorage.updateRequest(1L, requestDto))
                .isInstanceOf(InvalidDateRangeException.class)
                .hasMessageContaining("Invalid date range!");
    }

}