package com.project.w3t.controller;

import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestDto;
import com.project.w3t.model.request.RequestStatus;
import com.project.w3t.model.request.RequestType;
import com.project.w3t.service.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RequestControllerTest {

    @Mock
    private RequestService requestService;
    RequestController requestController;
    private final Long requestId = 1L;
    private final Request request = new Request(1L, "123", RequestType.HOLIDAY,
            "comment", LocalDate.now(), LocalDate.of(2023, 3, 1),
            LocalDate.of(2023, 3, 3), LocalDate.of(2023, 3, 10),
            RequestStatus.PENDING);

    private final RequestDto requestDto = new RequestDto(LocalDate.of(2023, 4, 1),
            LocalDate.of(2023, 4, 3), RequestType.OVERTIME, "comment");

    @BeforeEach
    void setUp() {
        requestController = new RequestController(requestService);
    }

    @Test
    void shouldGetAllRequests() {
        requestController.getAllRequests();
        verify(requestService).getAllRequests();
    }

    @Test
    void shouldAddRequest() {
        requestController.addRequest(request);
        verify(requestService).addRequest(request);
    }

    @Test
    void shouldUpdateRequest() {
        requestController.updateRequest(requestId, requestDto);
        verify(requestService).updateRequest(requestId, requestDto);
    }

    @Test
    void shouldDeleteRequest() {
        requestController.deleteRequest(requestId);
        verify(requestService).deleteRequest(requestId);
    }

    @Test
    void shouldGetAllRequestsByType() {
        RequestType requestTypeHoliday = RequestType.HOLIDAY;
        requestController.getAllRequestsByType(requestTypeHoliday);
        verify(requestService).getAllRequestsByType(requestTypeHoliday);
    }

    @Test
    void shouldGetRequestById() {
        requestController.getRequestById(requestId);
        verify(requestService).getRequestByRequestId(requestId);
    }

}