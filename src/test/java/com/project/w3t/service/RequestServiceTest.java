package com.project.w3t.service;

import com.project.w3t.exceptions.BadRequest400.BadRequestException;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestDto;
import com.project.w3t.model.request.RequestStatus;
import com.project.w3t.model.request.RequestType;
import com.project.w3t.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @Mock
    private RequestRepository requestRepository;
    private RequestService requestService;
    private final Request request = new Request(1L, "123", RequestType.HOLIDAY,
            "comment", LocalDate.now(), LocalDate.of(2023, 3, 1),
            LocalDate.of(2023, 3, 3), LocalDate.of(2023, 3, 10),
            RequestStatus.PENDING, null);


    private final RequestDto requestDto = new RequestDto(LocalDate.of(2023, 4, 1),
            LocalDate.of(2023, 4, 3), RequestType.OVERTIME, "comment");

    @BeforeEach
    void setUp() {
        requestService = new RequestService(requestRepository, userRepository);
    }
    @Test
    void shouldThrowWhenRequestsTableIsEmpty() {
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
        //when
        requestService.updateRequest(requestId, requestDto);
        //then
        verify(requestRepository).save(request);
    }

    @Test
    void shouldThrowWhenEndDateIsBeforeStartDateForUpdatingRequest() {
        //given
        RequestDto requestDtoWithStartDateAfterEndDate = new RequestDto(LocalDate.of(2023, 4, 10),
                LocalDate.of(2023, 4, 3), RequestType.OVERTIME, "comment");
        Long requestId = 1L;
        when(requestRepository.existsById(requestId)).thenReturn(true);
        when(requestService.getRequestByRequestId(requestId)).thenReturn(Optional.ofNullable(request));
        //when //then
        assertThatThrownBy(() -> requestService.updateRequest(requestId, requestDtoWithStartDateAfterEndDate))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range.");
    }

    @Test
    void shouldThrowWhenDateRangeIsBeforeRegistrationDateForUpdatingRequest() {
        //given
        RequestDto requestDtoWithDateRangeBeforeRegistrationDate = new RequestDto(LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 3), RequestType.OVERTIME, "comment");
        requestService.addRequest(request);
        Long requestId = 1L;
        when(requestRepository.existsById(requestId)).thenReturn(true);
        when(requestService.getRequestByRequestId(requestId)).thenReturn(Optional.ofNullable(request));
        //when //then
        assertThatThrownBy(() -> requestService.updateRequest(requestId, requestDtoWithDateRangeBeforeRegistrationDate))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range.");
    }

    @Test
    void shouldThrowWhenStartDateIsNullForUpdatingRequest() {
        //given
        Long requestId = 1L;
        when(requestRepository.existsById(requestId)).thenReturn(true);
        when(requestService.getRequestByRequestId(requestId)).thenReturn(Optional.ofNullable(request));
        requestDto.setStartDate(null);
        //when //then
        assertThatThrownBy(() -> requestService.updateRequest(requestId,requestDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range.");
    }

    @Test
    void shouldThrowWhenEndDateIsNullForUpdatingRequest() {
        //given
        Long requestId = 1L;
        when(requestRepository.existsById(requestId)).thenReturn(true);
        when(requestService.getRequestByRequestId(requestId)).thenReturn(Optional.ofNullable(request));
        requestDto.setEndDate(null);
        //when //then
        assertThatThrownBy(() -> requestService.updateRequest(requestId,requestDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range.");
    }

    @Test
    void shouldThrowWhenCommentIsTooLongForUpdatingRequest() {
        //given
        Long requestId = 1L;
        when(requestRepository.existsById(requestId)).thenReturn(true);
        when(requestService.getRequestByRequestId(requestId)).thenReturn(Optional.ofNullable(request));
        requestDto.setComment("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor");
        //when //then
        assertThatThrownBy(() -> requestService.updateRequest(requestId, requestDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Comment is too long.");
    }

    @Test
    void shouldGetAllRequestsByType() {
        RequestType requestTypeHoliday = RequestType.HOLIDAY;
        requestService.getAllRequestsByType(requestTypeHoliday);
        verify(requestRepository).findAllByType(requestTypeHoliday);
    }

    @Test
    void shouldDeleteRequest() {
        //given
        Long requestId = 1L;
        when(requestRepository.existsById(requestId)).thenReturn(true);

        //when
        requestService.deleteRequest(requestId);

        //then
        verify(requestRepository).deleteById(requestId);
    }

    @Test
    void shouldThrowBadRequestExceptionWhenThereIsNoRequestWithGivenId() throws BadRequestException{
        //given
        Long requestId = 1L;
        when(requestRepository.existsById(requestId)).thenReturn(false);

        //when //then
        assertThatThrownBy(() -> requestService.deleteRequest(requestId))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Request with this id does not exists.");
    }
    @Test
    void shouldReturnRequestById() {
        //given
        Long requestId = 1L;
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