package com.project.w3t.controller;

import com.project.w3t.model.Request;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/requests")
@Component
public class RequestsController {

    @GetMapping
    public List<Request> getAll() {
        List<Request> templist = new ArrayList<>();
        return templist;
    }
}
