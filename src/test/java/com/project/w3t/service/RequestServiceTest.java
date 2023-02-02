package com.project.w3t.service;

import com.project.w3t.exceptions.BadRequest400.BadRequestException;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestDto;
import com.project.w3t.model.request.RequestStatus;
import com.project.w3t.model.request.RequestType;
import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserType;
import com.project.w3t.repository.RequestRepository;
import com.project.w3t.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @Mock
    private RequestRepository requestRepository;
    @Mock
    private UserRepository userRepository;
    private RequestService requestService;
    private final String userId = "123";
    private final String managerId = "MANAGERID";
    private final User user = new User(1L, "email@email.com", userId, null, null, 10, UserType.EMPLOYEE, managerId, "TEAMID", null);
    private final Request request = new Request(1L, "123", RequestType.HOLIDAY,
            "comment", LocalDate.now(), LocalDate.of(2023, 3, 1),
            LocalDate.of(2023, 3, 3), LocalDate.of(2023, 3, 10),
            RequestStatus.PENDING, user);


    private final RequestDto requestDto = new RequestDto(LocalDate.of(2023, 4, 1),
            LocalDate.of(2023, 4, 3), RequestType.OVERTIME, "comment");

    private final Long requestId = 1L;

    @BeforeEach
    void setUp() {
        requestService = new RequestService(requestRepository, userRepository);
    }
    @Test
    void shouldReturnAllRequests() {
        //given
        List<Request> allRequests = new ArrayList<>(Arrays.asList(request));
        when(requestRepository.findAll()).thenReturn(allRequests);
        //when
        requestService.getAllRequests();
        //then
        verify(requestRepository, times(2)).findAll();
    }
    @Test
    void shouldThrowWhenRequestsListIsEmpty() {
        assertThatThrownBy(() -> requestService.getAllRequests())
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Could not find any requests.");
    }

    @Test
    void shouldAddRequest() {
        //given
        Request correctRequest = new Request(2L, "123", RequestType.HOLIDAY,
                "comment", LocalDate.now(), LocalDate.of(2023, 5, 1),
                LocalDate.of(2023, 5, 3), LocalDate.of(2023, 5, 10),
                RequestStatus.PENDING, null);
        //when
        when(userRepository.findByUserId(userId)).thenReturn(user);
        requestService.addRequest(correctRequest);
        //then
        verify(requestRepository).save(correctRequest);
    }

    @Test
    void shouldThrowWhenStartDateIsNullForAddingRequest() {
        //given
        Request requestWithNullStartDate = new Request(2L, "123", RequestType.HOLIDAY,
                "comment", LocalDate.now(), null,
                LocalDate.of(2023, 3, 3), LocalDate.of(2023, 3, 10),
                RequestStatus.PENDING, null);
        //when //then
        assertThatThrownBy(() -> requestService.addRequest(requestWithNullStartDate))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range.");
    }

    @Test
    void shouldThrowWhenEndDateIsNullForAddingRequest() {
        //given
        Request requestWithNullEndDate = new Request(2L, "123", RequestType.HOLIDAY,
                "comment", LocalDate.now(),
                LocalDate.of(2023, 3, 3), null, LocalDate.of(2023, 3, 10),
                RequestStatus.PENDING, null);
        //when //then
        assertThatThrownBy(() -> requestService.addRequest(requestWithNullEndDate))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range.");
    }

    @Test
    void shouldThrowWhenEndDateIsBeforeStartDateForAddingRequest() {
        //given
        Request requestWithStartDateAfterEndDate = new Request(2L, "123", RequestType.HOLIDAY,
                "comment", LocalDate.now(), LocalDate.of(2023, 3, 10),
                LocalDate.of(2023, 3, 3), LocalDate.of(2023, 3, 10),
                RequestStatus.PENDING, null);
        //when //then
        assertThatThrownBy(() -> requestService.addRequest(requestWithStartDateAfterEndDate))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range.");
    }

    @Test
    void shouldThrowWhenDateRangeIsBeforeRegistrationDateForAddingRequest() {
        //given
        Request requestWithDateRangeBeforeRegistrationDate = new Request(2L, "123", RequestType.HOLIDAY,
                "comment", LocalDate.now(), LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 3), LocalDate.of(2023, 3, 10),
                RequestStatus.PENDING, null);
        //when //then
        assertThatThrownBy(() -> requestService.addRequest(requestWithDateRangeBeforeRegistrationDate))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range.");
    }

    @Test
    void shouldThrowWhenCommentIsTooLongForAddingRequest() {
        //given
        request.setComment("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor");
        //when //then
        assertThatThrownBy(() -> requestService.addRequest(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Comment is too long.");
    }

    @Test
    void shouldThrowWhenCommentIsNullForAddingRequest() {
        //given
        request.setComment(null);
        //when //then
        assertThatThrownBy(() -> requestService.addRequest(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Comment is too long.");
    }

    @Test
    void shouldUpdateRequest() {
        //given
        Long requestId = 1L;
        when(requestRepository.existsById(requestId)).thenReturn(true);
        when(requestService.getRequestByRequestId(requestId)).thenReturn(Optional.ofNullable(request));
        when(userRepository.existsByUserId(userId)).thenReturn(true);
        //when
        requestService.updateRequest(userId, requestId, requestDto);
        //then
        verify(requestRepository).save(request);
    }

    @Test
    void shouldThrowWhenEndDateIsBeforeStartDateForUpdatingRequest() {
        //given
        RequestDto requestDtoWithStartDateAfterEndDate = new RequestDto(LocalDate.of(2023, 4, 10),
                LocalDate.of(2023, 4, 3), RequestType.OVERTIME, "comment");
        when(requestRepository.existsById(requestId)).thenReturn(true);
        when(requestService.getRequestByRequestId(requestId)).thenReturn(Optional.ofNullable(request));
        //when //then
        assertThatThrownBy(() -> requestService.updateRequest(userId, requestId, requestDtoWithStartDateAfterEndDate))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range.");
    }

    @Test
    void shouldThrowWhenDateRangeIsBeforeRegistrationDateForUpdatingRequest() {
        //given
        RequestDto requestDtoWithDateRangeBeforeRegistrationDate = new RequestDto(LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 3), RequestType.OVERTIME, "comment");
        when(userRepository.findByUserId(userId)).thenReturn(user);
        requestService.addRequest(request);
        when(requestRepository.existsById(requestId)).thenReturn(true);
        when(requestService.getRequestByRequestId(requestId)).thenReturn(Optional.of(request));
        when(userRepository.existsByUserId(userId)).thenReturn(true);
        //when //then
        assertThatThrownBy(() -> requestService.updateRequest(userId, requestId, requestDtoWithDateRangeBeforeRegistrationDate))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range.");
    }

    @Test
    void shouldThrowWhenStartDateIsNullForUpdatingRequest() {
        //given
        when(requestRepository.existsById(requestId)).thenReturn(true);
        when(requestService.getRequestByRequestId(requestId)).thenReturn(Optional.ofNullable(request));
        requestDto.setStartDate(null);
        //when //then
        assertThatThrownBy(() -> requestService.updateRequest(userId, requestId, requestDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range.");
    }

    @Test
    void shouldThrowWhenEndDateIsNullForUpdatingRequest() {
        //given
        when(requestRepository.existsById(requestId)).thenReturn(true);
        when(requestService.getRequestByRequestId(requestId)).thenReturn(Optional.ofNullable(request));
        requestDto.setEndDate(null);
        //when //then
        assertThatThrownBy(() -> requestService.updateRequest(userId, requestId,requestDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range.");
    }
    @Test
    void shouldThrowWhenStartAndEndDateAreNullForUpdatingRequest() {
        //given
        Long requestId = 1L;
        when(requestRepository.existsById(requestId)).thenReturn(true);
        when(requestService.getRequestByRequestId(requestId)).thenReturn(Optional.ofNullable(request));
        requestDto.setStartDate(null);
        requestDto.setEndDate(null);
        //when //then
        assertThatThrownBy(() -> requestService.updateRequest(userId, requestId, requestDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range.");
    }

    @Test
    void shouldThrowWhenCommentIsTooLongForUpdatingRequest() {
        //given
        when(requestRepository.existsById(requestId)).thenReturn(true);
        when(requestService.getRequestByRequestId(requestId)).thenReturn(Optional.ofNullable(request));
        requestDto.setComment("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor");
        when(userRepository.existsByUserId(userId)).thenReturn(true);
        //when //then
        assertThatThrownBy(() -> requestService.updateRequest(userId, requestId, requestDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Comment is too long.");
    }

    @Test
    void shouldGetAllRequestsByType() {
        RequestType requestTypeHoliday = RequestType.HOLIDAY;
        List<Request> tempList = new ArrayList<>();
        Request request = new Request();
        tempList.add(request);
        when(requestRepository.findAllByType(requestTypeHoliday)).thenReturn(tempList);
        requestService.getAllRequestsByType(requestTypeHoliday);
        verify(requestRepository, times(2)).findAllByType(requestTypeHoliday);
    }

    @Test
    void shouldDeleteRequest() {
        //given
        when(requestRepository.existsById(requestId)).thenReturn(true);

        //when
        requestService.deleteRequest(requestId);

        //then
        verify(requestRepository).deleteById(requestId);
    }

    @Test
    void shouldThrowBadRequestExceptionWhenThereIsNoRequestWithGivenId() throws BadRequestException {
        //given
        when(requestRepository.existsById(requestId)).thenReturn(false);

        //when //then
        assertThatThrownBy(() -> requestService.deleteRequest(requestId))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Request with this id does not exists.");
    }

    @Test
    void shouldReturnRequestById() {
        //given
        when(requestRepository.existsById(requestId)).thenReturn(true);
        //when
        requestService.getRequestByRequestId(requestId);
        //then
        verify(requestRepository).findById(requestId);
    }

    @Test
    void shouldThrowWhenRequestNotExist() {
        //given
        Long requestId = -1L;
        //when //then
        assertThatThrownBy(() -> requestService.getRequestByRequestId(requestId))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Request with this id does not exists.");
    }
}