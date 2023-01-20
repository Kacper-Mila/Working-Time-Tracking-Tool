package com.project.w3t.service;

import com.project.w3t.exceptions.InvalidCommentLengthException;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.Request;
import com.project.w3t.model.Status;
import com.project.w3t.model.Type;
import com.project.w3t.repository.RequestRepository;
import com.project.w3t.repository.RequestStorage;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @Mock
    private RequestRepository requestRepository;
    private RequestService requestService;
    private Request request = new Request(1L, "123", Type.HOLIDAY,
            "comment", LocalDate.now(),LocalDate.of(2022, 2, 1),
            LocalDate.of(2022, 2, 3),LocalDate.of(2022, 2, 10),
            Status.PENDING );

    @BeforeEach
    void setUp() {
        requestService = new RequestService(requestRepository);
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
        List<Request> requestList = new ArrayList<>();
        requestList.add(request);

        when(requestRepository.getAllRequestsByType(type)).thenReturn(requestList);

        List<Request> actualList = new ArrayList<>();
        actualList = requestRepository.getAllRequestsByType(type);

        assertThat(requestList.equals(actualList));
    }

}
