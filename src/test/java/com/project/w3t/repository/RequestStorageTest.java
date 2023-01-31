//package com.project.w3t.repository;
//
//import com.project.w3t.exceptions.InvalidCommentLengthException;
//import com.project.w3t.exceptions.InvalidDateRangeException;
//import com.project.w3t.exceptions.InvalidRequestIdException;
//import com.project.w3t.model.request.Request;
//import com.project.w3t.model.request.RequestDto;
//import com.project.w3t.model.request.RequestStatus;
//import com.project.w3t.model.request.RequestType;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//
////@ExtendWith(MockitoExtension.class)
//class RequestStorageTest {
//    RequestStorage requestStorage = new RequestStorage();
//    private List<Request> userRequestList;
//    private final Request request1 = new Request(1L, "123", RequestType.HOLIDAY,
//            "comment", LocalDate.now(),LocalDate.of(2022, 1, 1),
//            LocalDate.of(2022, 1, 3),LocalDate.of(2022, 1, 10),
//            RequestStatus.PENDING );
//
//    private final Request request2 = new Request(5L, "1233", RequestType.OVERTIME,
//            "comment", LocalDate.now(),LocalDate.of(2023, 2, 1),
//            LocalDate.of(2023, 2, 3),LocalDate.of(2023, 2, 10),
//            RequestStatus.PENDING );
//
//    private final Request request3 = new Request(21L, "1233", RequestType.REMOTE,
//            "comment", LocalDate.now(),LocalDate.of(2023, 3, 1),
//            LocalDate.of(2023, 3, 3),LocalDate.of(2023, 3, 10),
//            RequestStatus.PENDING );
//    private final Request request4 = new Request(20L, "1233", RequestType.HOLIDAY,
//            "comment", LocalDate.now(),LocalDate.of(2023, 10, 1),
//            LocalDate.of(2023, 10, 3),LocalDate.of(2023, 10, 10),
//            RequestStatus.PENDING );
//
//    private final RequestDto requestDto = new RequestDto(LocalDate.of(2022, 4, 1),
//            LocalDate.of(2022, 4, 3), RequestType.OVERTIME, "updated comment");
//
//    @BeforeEach
//    void setUp() throws InvalidCommentLengthException {
//        userRequestList = new ArrayList<>();
//        //TODO add requests to list in here
//        requestStorage.addRequest(request1);
//        requestStorage.addRequest(request2);
//        requestStorage.addRequest(request3);
//    }
//
//    @Test
//    void shouldAddRequest() throws InvalidDateRangeException, InvalidCommentLengthException {
//        int startSize = requestStorage.getAllRequests().size();
//        requestStorage.addRequest(request4);
//
//        assertThat(requestStorage.getAllRequests().size()).isEqualTo(startSize + 1);
//    }
//
//    @Test
//    void shouldThrowWhenDateRangeIsWrong() throws InvalidCommentLengthException {
//
//        assertThatThrownBy(() -> requestStorage.addRequest(request1))
//                .isInstanceOf(InvalidDateRangeException.class)
//                .hasMessageContaining("Invalid date range!");
//
//    }
//
//    @Test
//    void shouldThrowInvalidCommentLengthExceptionWhenCommentIsTooLongForAddMethod() {
//        request4.setComment("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
//                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
//                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
//                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor");
//
//        assertThatThrownBy(() -> requestStorage.addRequest(request4))
//                .isInstanceOf(InvalidCommentLengthException.class)
//                .hasMessageContaining("Your comment is invalid!");
//
//    }
//
//    @Test
//    void shouldThrowInvalidCommentLengthExceptionWhenCommentIsNullForAddMethod() {
//        request4.setComment(null);
//        assertThatThrownBy(() -> requestStorage.addRequest(request4))
//                .isInstanceOf(InvalidCommentLengthException.class)
//                .hasMessageContaining("Your comment is invalid!");
//
//    }
//
//    @Test
//    void shouldDeleteRequest() throws InvalidCommentLengthException, InvalidRequestIdException {
//        int requestsAmount = requestStorage.getAllRequests().size();
//        requestStorage.deleteRequest(1L);
//
//        assertThat(requestStorage.getAllRequests().size()).isEqualTo(requestsAmount - 1);
//    }
//
//    @Test
//    void shouldThrowInvalidRequestIdExceptionWhenInvalidRequestIdForDeleteRequestMethod() throws InvalidCommentLengthException {
//
//        assertThatThrownBy(() -> requestStorage.deleteRequest(2L))
//                .isInstanceOf(InvalidRequestIdException.class)
//                .hasMessageContaining("Invalid request Id!");
//    }
//
//    @Test
//    void shouldReturnAllRequestsByOvertimeType() {
//        //TODO Use created list in BeforeEach, add requests in BeforeEach
//
//        List<Request> expected = new ArrayList<>();
//        expected.add(request2);
//
//        assertThat(requestStorage.getAllRequestsByType("overtime")).isEqualTo(expected);
//    }
//
//    @Test
//    void shouldReturnAllRequestsByRemoteType() {
//        //TODO Use created list in BeforeEach, add requests in BeforeEach
//
//        List<Request> expected = new ArrayList<>();
//
//        expected.add(request3);
//
//
//        assertThat(requestStorage.getAllRequestsByType("remote")).isEqualTo(expected);
//    }
//
//    @Test
//    void shouldReturnAllRequestsByHolidayType() {
//        //TODO Use created list in BeforeEach, add requests in BeforeEach
//
//        List<Request> expected = new ArrayList<>();
//        expected.add(request1);
//
//        assertThat(requestStorage.getAllRequestsByType("holiday")).isEqualTo(expected);
//    }
//
//
//    // MA TO SENS? MA TO SENS? MA TO SENS???
//    // MA TO SENS? MA TO SENS? MA TO SENS???
//    // MA TO SENS? MA TO SENS? MA TO SENS???
//    // MA TO SENS? MA TO SENS? MA TO SENS???
//    @Test
//    void shouldReturnAllRequestsByTypeNegative() {
//        //TODO Use created list in BeforeEach, add requests in BeforeEach
//
//        List<Request> expected = new ArrayList<>();
//
//        expected.add(request1);
//        expected.add(request2);
//
//        assertThat(requestStorage.getAllRequestsByType("holiday"))
//                .doesNotHaveSameHashCodeAs(expected);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenRequestTypeDoesNotMatch() {
//        assertThatThrownBy(() -> requestStorage.getAllRequestsByType("hollidaaay"))
//                .isInstanceOf(NullPointerException.class);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenRequestTypeIsEmpty() {
//        assertThatThrownBy(() -> requestStorage.getAllRequestsByType(""))
//                .isInstanceOf(NullPointerException.class);
//    }
//
//    @Test
//    void shouldUpdateRequestType() throws InvalidCommentLengthException, InvalidRequestIdException {
//        Long id = 1L;
//        requestStorage.updateRequest(id, requestDto);
//
//        RequestType expectedRequestType = RequestType.OVERTIME;
//
//        assertThat(requestStorage.getRequestById(id).getType()).isEqualTo(expectedRequestType);
//    }
//
//    @Test
//    void shouldUpdateRequestStartDate() throws InvalidCommentLengthException, InvalidRequestIdException {
//        Long id = 1L;
//        requestStorage.updateRequest(id, requestDto);
//
//        LocalDate expectedStartDate = requestDto.getStartDate();
//
//        assertThat(requestStorage.getRequestById(id).getStartDate()).isEqualTo(expectedStartDate);
//    }
//
//    @Test
//    void shouldUpdateRequestEndDate() throws InvalidCommentLengthException, InvalidRequestIdException {
//        Long id = 1L;
//        requestStorage.updateRequest(id, requestDto);
//
//        LocalDate expectedEndDate = requestDto.getEndDate();
//
//        assertThat(requestStorage.getRequestById(id).getEndDate()).isEqualTo(expectedEndDate);
//    }
//
//    @Test
//    void shouldUpdateRequestComment() throws InvalidCommentLengthException, InvalidRequestIdException {
//        Long id = 1L;
//        requestStorage.updateRequest(id, requestDto);
//
//        String expectedComment = requestDto.getComment();
//
//        assertThat(requestStorage.getRequestById(id).getComment()).isEqualTo(expectedComment);
//    }
//
//    @Test
//    void shouldUpdateRequestStatusToPending() throws InvalidCommentLengthException, InvalidRequestIdException {
//        Long id = 1L;
//        requestStorage.updateRequest(id, requestDto);
//
//        RequestStatus expectedStatus = RequestStatus.PENDING;
//
//        assertThat(requestStorage.getRequestById(id).getStatus()).isEqualTo(expectedStatus);
//    }
//
//    @Test
//    void shouldThrowInvalidCommentLengthExceptionWhenCommentIsTooLongForUpdateMethod() throws InvalidCommentLengthException {
//        requestDto.setComment("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
//                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
//                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
//                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor");
//
//        assertThatThrownBy(() -> requestStorage.updateRequest(1L, requestDto))
//                .isInstanceOf(InvalidCommentLengthException.class)
//                .hasMessageContaining("Your comment is invalid!");
//    }
//
//    @Test
//    void shouldThrowInvalidDateRangeExceptionWhenDateRangeIsWrongForUpdateMethod() throws InvalidCommentLengthException {
//        requestStorage.addRequest(request4);
//        requestDto.setStartDate(request4.getStartDate());
//        requestDto.setEndDate(request4.getEndDate());
//
//        assertThatThrownBy(() -> requestStorage.updateRequest(1L, requestDto))
//                .isInstanceOf(InvalidDateRangeException.class)
//                .hasMessageContaining("Invalid date range!");
//    }
//
//    @Test
//    void shouldThrowInvalidRequestIdExceptionWhenIdIsInvalidForUpdateMethod() throws InvalidCommentLengthException {
//        assertThatThrownBy(() -> requestStorage.updateRequest(3L, requestDto))
//                .isInstanceOf(InvalidRequestIdException.class)
//                .hasMessageContaining("Invalid request Id!");
//    }
//
//    @Test
//    void shouldThrowInvalidRequestIdExceptionWhenIdIsInvalidForGetRequestByIdMethod() throws InvalidCommentLengthException {
//        assertThatThrownBy(() -> requestStorage.getRequestById(3L))
//                .isInstanceOf(InvalidRequestIdException.class)
//                .hasMessageContaining("Invalid request Id!");
//    }
//
//    //ID TESTS
//    @Test
//    void shouldReturnRequestById() throws InvalidRequestIdException {
//        assertThat(requestStorage.getRequestById(21L)).isEqualTo(request3);
//    }
//    @Test
//    void shouldThrowInvalidRequestIdException(){
//        assertThatThrownBy(() -> requestStorage.getRequestById(2137L)).isInstanceOf(Exception.class);
//    }
//}