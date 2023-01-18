package com.project.w3t.controller;

import com.project.w3t.model.Request;
import com.project.w3t.repository.RequestStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestsController {

    @Autowired
    RequestStorage requestStorage;

    @GetMapping
    public List<Request> getAll() {
        return requestStorage.getUserRequestList();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Request getRequestById(@PathVariable Long requestId){
        return requestStorage.getRequestById(requestId);
    }
}
