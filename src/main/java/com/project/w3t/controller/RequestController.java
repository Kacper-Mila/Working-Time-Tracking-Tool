package com.project.w3t.controller;

import com.project.w3t.exceptions.BadRequest400.BadRequestException;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestDto;
import com.project.w3t.model.request.RequestType;
import com.project.w3t.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public void addRequest(@RequestBody Request request) {
        requestService.addRequest(request);
    }

    @PatchMapping("/update")
    public void updateRequest(@RequestParam Long requestId, @RequestBody RequestDto requestDto) {
        requestService.updateRequest(requestId, requestDto);
    }

    @DeleteMapping("/delete")
    public void deleteRequest(@RequestParam Long requestId) {
        requestService.deleteRequest(requestId);
    }

    @GetMapping("/type")
    public List<Request> getAllRequestsByType(@RequestParam RequestType requestType) {
        return requestService.getAllRequestsByType(requestType);
    }

    @GetMapping("/id")
    @ResponseBody
    public Object getRequestById(@RequestParam Long requestId) {
        return requestService.getRequestByRequestId(requestId);
    }
}