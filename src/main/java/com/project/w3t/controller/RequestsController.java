package com.project.w3t.controller;

import com.project.w3t.exceptions.InvalidCommentLength;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.model.Request;
import com.project.w3t.repository.RequestStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping("")
    public List<Request> getAll() {
        return requestStorage.getUserRequestList();
    }

    @PostMapping("")
    public void addRequest(@RequestBody Request request) {
        try {
            requestStorage.addRequest(request);
        } catch (InvalidDateRangeException e) {
            System.out.println("Invalid date range!");
        } catch (InvalidCommentLength e) {
            System.out.println("Your comment is too long!");
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Request getRequestById(@PathVariable Long requestId){
        return requestStorage.getRequestById(requestId);
    }
}
