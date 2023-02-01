package com.project.w3t.controller;

import com.project.w3t.model.request.Request;
import com.project.w3t.repository.RequestStorage;
import org.junit.jupiter.api.Test;

class RequestsControllerTest {


    RequestStorage requestStorage = new RequestStorage();
//    RequestsController requestsController = new RequestsController(requestStorage);


    @Test
    public void shouldReturnAllRequestHistory() {
//      given
        Request tempRequest = new Request();
        Request tempRequest1 = new Request();
        Request tempRequest2 = new Request();
        Request tempRequest3 = new Request();
//        requestStorage.addRequest(tempRequest);
//        requestStorage.addRequest(tempRequest1);
//        requestStorage.addRequest(tempRequest2);
//        requestStorage.addRequest(tempRequest3);
//      when request route
//        List<Request> requests = requestsController.getAll();
//      then return list of requests
//        Assertions.assertThat(requests.contains(tempRequest));
//        Assertions.assertThat(requests.size() == 4);
    }
}