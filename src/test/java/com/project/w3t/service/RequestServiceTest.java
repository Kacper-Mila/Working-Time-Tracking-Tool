package com.project.w3t.service;

import com.project.w3t.exceptions.InvalidCommentLengthException;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestDto;
import com.project.w3t.model.request.RequestStatus;
import com.project.w3t.model.request.RequestType;
import com.project.w3t.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @Mock
    private RequestRepository requestRepository;
    private RequestService requestService;
    private final Request request = new Request(1L, "123", RequestType.HOLIDAY,
            "comment", LocalDate.now(),LocalDate.of(2022, 2, 1),
            LocalDate.of(2022, 2, 3),LocalDate.of(2022, 2, 10),
            RequestStatus.PENDING );

    @BeforeEach
    void setUp() {
        requestService = new RequestService(requestRepository);
    }

    @Test
    void shouldGetAllRequests() {
        requestService.getAllRequests();

        verify(requestRepository).getAllRequests();
    }

    @Test
    void shouldAddRequest() throws InvalidCommentLengthException {
        requestService.addRequest(request);

        ArgumentCaptor<Request> argumentCaptor = ArgumentCaptor.forClass(Request.class);

        verify(requestRepository).addRequest(argumentCaptor.capture());

        Request capturedRequest = argumentCaptor.getValue();

        assertThat(capturedRequest).isEqualTo(request);
    }

    @Test
    void shouldDeleteRequest() throws InvalidRequestIdException {
        Long requestId = 1L;

        requestService.deleteRequest(requestId);

        verify(requestRepository).deleteRequest(requestId);
    }

    @Test
    void shouldGetAllRequestsByType() throws InvalidCommentLengthException {
        String type = "holiday";
        requestService.getAllRequestsByType(type);

        verify(requestRepository).getAllRequestsByType(type);
    }
}
