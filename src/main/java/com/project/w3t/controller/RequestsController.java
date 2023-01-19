package com.project.w3t.controller;

import com.project.w3t.exceptions.InvalidCommentLength;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.Request;
import com.project.w3t.model.RequestDto;
import com.project.w3t.model.Type;
import com.project.w3t.repository.RequestStorage;
import com.project.w3t.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/requests")
public class RequestsController {

    private final RequestService requestService;
    private final RequestStorage requestStorage;
    @Autowired
    public RequestsController(RequestStorage requestStorage, RequestService requestService) {
        this.requestService = requestService;
        this.requestStorage = requestStorage;
    }

    @GetMapping
    public List<Request> getAll() {
        return requestStorage.getUserRequestList();
    }

    @PostMapping
    public void addRequest(@RequestBody Request request) {
        requestService.addRequest(request);
    }

    @PatchMapping("{requestId}")
    public void update(@PathVariable Long requestId, @RequestBody RequestDto requestDto) {
        requestService.updateRequest(requestId, requestDto);
    }

    @DeleteMapping("{requestId}")
    public void delete(@PathVariable Long requestId) {
        requestService.deleteRequest(requestId);
    }

    @GetMapping("/type/{type}")
    public List<Request> getAllByType(@PathVariable("type")Type requestType) {
        return requestStorage.getUserRequestListByType(requestType);
    }

    @GetMapping("/id/{requestId}")
    @ResponseBody
    public Object getRequestById(@PathVariable Long requestId){
        try{
            return requestStorage.getRequestById(requestId);
        }catch (InvalidRequestIdException e){
            System.out.println("Wrong request ID!");
            return null;
        }
    }
}
