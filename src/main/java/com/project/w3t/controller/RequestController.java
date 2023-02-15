package com.project.w3t.controller;

import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestDto;
import com.project.w3t.model.request.RequestType;
import com.project.w3t.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
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
    public void updateRequest(@RequestParam String userId, @RequestParam Long requestId, @RequestBody RequestDto requestDto) {
        requestService.updateRequest(userId, requestId, requestDto);
    }

    @DeleteMapping("/delete")
    public void deleteRequest(@RequestParam Long requestId) {
        requestService.deleteRequest(requestId);
    }

//    not needed
    @GetMapping("/type")
    public List<Request> getAllRequestsByType(@RequestParam RequestType requestType) {
        return requestService.getAllRequestsByType(requestType);
    }

//    not needed?
    @GetMapping("/id")
    @ResponseBody
    public Object getRequestById(@RequestParam Long requestId) {
        return requestService.getRequestByRequestId(requestId);
    }

    @GetMapping("/userId")
    public List<Request> getRequestsByUserId(@RequestParam String userId) {
        return requestService.getRequestsByUserId(userId);
    }

    @GetMapping("/managerId")
    public List<Request> getEmployeesRequestsByManagerId(@RequestParam String managerId) {
        return requestService.getEmployeesRequestsByManagerId(managerId);
    }
}