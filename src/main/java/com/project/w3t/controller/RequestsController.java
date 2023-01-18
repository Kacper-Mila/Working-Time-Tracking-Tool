package com.project.w3t.controller;

import com.project.w3t.model.Request;
import com.project.w3t.model.Type;
import com.project.w3t.repository.RequestStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/requests")
public class RequestsController {

    private final RequestStorage requestStorage;
    @Autowired
    public RequestsController(RequestStorage requestStorage) {
        this.requestStorage = requestStorage;
    }

    @GetMapping
    public List<Request> getAll() {
        return requestStorage.getUserRequestList();
    }

    @GetMapping("/{type}")
    public List<Request> getAllByType(@PathVariable("type")Type requestType) {
        return requestStorage.getUserRequestListByType(requestType);
    }
}
