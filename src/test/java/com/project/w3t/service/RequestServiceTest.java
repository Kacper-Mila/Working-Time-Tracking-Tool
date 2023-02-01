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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @Mock
    private RequestRepository requestRepository;
    private RequestService requestService;
    private Request request = new Request(1L, "123", RequestType.HOLIDAY,
            "comment", LocalDate.now(), LocalDate.of(2023, 3, 1),
            LocalDate.of(2023, 3, 3), LocalDate.of(2023, 3, 10),
            RequestStatus.PENDING);


    private RequestDto requestDto = new RequestDto(LocalDate.of(2023, 4, 1),
            LocalDate.of(2023, 4, 3), RequestType.OVERTIME, "comment");

    @BeforeEach
    void setUp() {
        requestService = new RequestService(requestRepository);
    }

    @Test
    void shouldAddRequest() {
        Request correctRequest = new Request(2L, "123", RequestType.HOLIDAY,
                "comment", LocalDate.now(), LocalDate.of(2023, 5, 1),
                LocalDate.of(2023, 5, 3), LocalDate.of(2023, 5, 10),
                RequestStatus.PENDING);
        requestService.addRequest(correctRequest);
        verify(requestRepository).save(correctRequest);
    }

    @Test
    void shouldThrowWhenEndDateIsBeforeStartDateForAddingRequest() {
        Request requestWithStartDateAfterEndDate = new Request(2L, "123", RequestType.HOLIDAY,
                "comment", LocalDate.now(), LocalDate.of(2023, 3, 10),
                LocalDate.of(2023, 3, 3), LocalDate.of(2023, 3, 10),
                RequestStatus.PENDING);

        assertThatThrownBy(() -> requestService.addRequest(requestWithStartDateAfterEndDate))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range.");
    }

    @Test
    void shouldThrowWhenDateRangeIsBeforeRegistrationDateForAddingRequest() {
        Request requestWithDateRangeBeforeRegistrationDate = new Request(2L, "123", RequestType.HOLIDAY,
                "comment", LocalDate.now(), LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 3), LocalDate.of(2023, 3, 10),
                RequestStatus.PENDING);

        assertThatThrownBy(() -> requestService.addRequest(requestWithDateRangeBeforeRegistrationDate))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Invalid date range.");
    }

    @Test
    void shouldThrowWhenCommentIsTooLongForAddingRequest() {
        request.setComment("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor");


        assertThatThrownBy(() -> requestService.addRequest(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Comment is too long.");
    }

    @Test
    void shouldUpdateRequest() {
        requestService.addRequest(request);
        Long requestId = 1L;
        when(requestRepository.existsById(requestId)).thenReturn(true);
        when(requestService.getRequestByRequestId(requestId)).thenReturn(Optional.ofNullable(request));
        requestService.updateRequest(requestId, requestDto);
        verify(requestRepository, times(2)).save(request);
    }

    @Test
    void shouldThrowWhenEndDateIsBeforeStartDateForUpdatingRequest() {
        //given
        RequestDto requestDtoWithStartDateAfterEndDate = new RequestDto(LocalDate.of(2023, 4, 10),
                LocalDate.of(2023, 4, 3), RequestType.OVERTIME, "comment");
        requestService.addRequest(request);
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
    void shouldThrowWhenCommentIsTooLongForUpdatingRequest() {
        requestService.addRequest(request);
        Long requestId = 1L;
        when(requestRepository.existsById(requestId)).thenReturn(true);
        when(requestService.getRequestByRequestId(requestId)).thenReturn(Optional.ofNullable(request));
        requestDto.setComment("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor" +
                "Praesent rutrum, massa eget iaculis mollis, neque magna lacinia mi, id feugiat tellus lectus quis tortor");


        assertThatThrownBy(() -> requestService.updateRequest(requestId, requestDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Comment is too long.");
    }


}