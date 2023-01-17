package com.project.w3t.controller;

import com.project.w3t.model.Request;
import com.project.w3t.repository.RequestStorage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class RequestsControllerTest {


    RequestsController requestsController = new RequestsController();
    RequestStorage requestStorage = new RequestStorage();


    @Test
    public void shouldReturnAllRequestHistory() {
//      given
        Request tempRequest = new Request();
        Request tempRequest1 = new Request();
        Request tempRequest2 = new Request();
        Request tempRequest3 = new Request();
        requestStorage.addRequest(tempRequest);
        requestStorage.addRequest(tempRequest1);
        requestStorage.addRequest(tempRequest2);
        requestStorage.addRequest(tempRequest3);
//      when request route
        List<Request> requests = requestsController.getAll();
//      then return list of requests
        Assertions.assertThat(requests.contains(tempRequest));
        Assertions.assertThat(requests.size() == 4);
    }
}