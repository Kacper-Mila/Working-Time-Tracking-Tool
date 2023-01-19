package com.project.w3t.controller;

import com.project.w3t.model.Request;
import com.project.w3t.repository.RequestStorage;
import com.project.w3t.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/requests")
public class RequestsController {

    @Autowired
    RequestStorage requestStorage;

    @Autowired
    RequestService requestService;


    @GetMapping("")
    public List<Request> getAll() {
        return requestStorage.getUserRequestList();
    }

    @PostMapping("")
    public void addRequest(@RequestBody Request request) {
        requestService.addRequest(request);
    }

    @GetMapping("/{requestId}")
    @ResponseBody
    public Object getRequestById(@PathVariable Long requestId){
        return requestService.getRequestById(requestId);
    }
}
