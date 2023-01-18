package com.project.w3t.controller;

import com.project.w3t.model.Request;
import com.project.w3t.repository.RequestStorage;
import com.project.w3t.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/requests")
public class RequestsController {

    @Autowired
    RequestStorage requestStorage;

    @Autowired
    RequestService requestService;


    @GetMapping
    public List<Request> getAll() {
        return requestStorage.getUserRequestList();
    }

    @PostMapping
    public void addRequest(@RequestBody Request request) {
        requestService.addRequest(request);
    }

    @PutMapping("{id}")
    public void update(@PathVariable Long id, @RequestBody Request request) {
        try {
            requestStorage.updateRequest(id, request.getType(), request.getStartDate(), request.getEndDate(), request.getComment());
        } catch (InvalidRequestId e) {
            System.out.println("Invalid request Id!");
        }
    }
}
