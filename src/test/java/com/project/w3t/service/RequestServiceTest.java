package com.project.w3t.service;

import com.project.w3t.exceptions.BadRequest400.BadRequestException;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestStatus;
import com.project.w3t.model.request.RequestType;
import com.project.w3t.repository.RequestRepository;
import com.project.w3t.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.NestedTestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {
    @Mock
    RequestRepository requestRepository;
    RequestService requestService;

    @BeforeEach
    void setUp() {
        requestService = new RequestService(requestRepository);
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
}