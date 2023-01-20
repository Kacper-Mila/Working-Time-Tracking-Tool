package com.project.w3t.controller;

import com.project.w3t.exceptions.InvalidCommentLengthException;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.Request;
import com.project.w3t.model.RequestDto;
import com.project.w3t.model.Type;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestDto;
import com.project.w3t.model.request.RequestType;
import com.project.w3t.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/requests")
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public List<Request> getAllRequests() {
        return requestService.getAllRequests();
    }

    @PostMapping
    public void addRequest(@RequestBody Request request) throws InvalidDateRangeException, InvalidCommentLengthException {
        requestService.addRequest(request);
    }

    @PatchMapping("/update")
    public void updateRequest(@RequestParam Long requestId, @RequestBody RequestDto requestDto) throws InvalidRequestIdException, InvalidDateRangeException, InvalidCommentLengthException {
        requestService.updateRequest(requestId, requestDto);
    }

    @DeleteMapping("/delete")
    public void deleteRequest(@RequestParam Long requestId) {
        requestService.deleteRequest(requestId);
    }

    @GetMapping("/type")
    public Optional<List<Request>> getAllRequestsByType(@RequestParam String requestType) {
        return requestService.getAllRequestsByType(requestType);
    }

    @GetMapping("/id")
    @ResponseBody
    public Object getRequestById(@RequestParam Long requestId) {
        return requestService.getRequestById(requestId);
    }
}
