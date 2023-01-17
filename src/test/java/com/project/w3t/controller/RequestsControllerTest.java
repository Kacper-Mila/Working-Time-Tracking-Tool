package com.project.w3t.controller;

import com.project.w3t.model.Request;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class RequestsControllerTest {

    RequestsController requestsController = new RequestsController();

    @Test
    public void shouldReturnAllRequestHistory() {
//      given
//        Request tempRequest = new Request();
//      when request route
        List<Request> requests = requestsController.getAll();
//      then return list of requests
        Assertions.assertThat(requests.isEmpty());
    }
}