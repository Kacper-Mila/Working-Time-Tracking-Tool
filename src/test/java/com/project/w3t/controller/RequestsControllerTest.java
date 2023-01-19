package com.project.w3t.controller;

import com.project.w3t.model.Request;
import com.project.w3t.model.Status;
import com.project.w3t.model.Type;
import com.project.w3t.repository.RequestStorage;
import com.project.w3t.service.RequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RequestsController.class)
public class RequestsControllerTest {


//    @Test
//    public void shouldReturnAllRequestHistory() {
////      given
//        Request tempRequest = new Request();
//        Request tempRequest1 = new Request();
//        Request tempRequest2 = new Request();
//        Request tempRequest3 = new Request();
//        requestStorage.addRequest(tempRequest);
//        requestStorage.addRequest(tempRequest1);
//        requestStorage.addRequest(tempRequest2);
//        requestStorage.addRequest(tempRequest3);
////      when request route
//        List<Request> requests = requestsController.getAll();
////      then return list of requests
//        Assertions.assertThat(requests.contains(tempRequest));
//        Assertions.assertThat(requests.size() == 4);
//    }
}