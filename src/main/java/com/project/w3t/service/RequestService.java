package com.project.w3t.service;

import com.project.w3t.exceptions.InvalidCommentLengthException;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.Request;
import com.project.w3t.model.RequestDto;
import com.project.w3t.model.Type;
import com.project.w3t.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> getAllRequests() {
        return requestRepository.getAllRequests();
    }

    public void addRequest(Request request) throws InvalidDateRangeException, InvalidCommentLengthException {
            requestRepository.addRequest(request);
    }

    public void updateRequest(Long id, RequestDto requestDto) throws InvalidRequestIdException, InvalidDateRangeException, InvalidCommentLengthException {
            requestRepository.updateRequest(id, requestDto);
    }

    public void deleteRequest(Long requestId) {
        try {
            requestRepository.deleteRequest(requestId);
        } catch (InvalidRequestIdException e) {
            System.out.println("Invalid request Id!");
        }
    }

    public Optional<List<Request>> getAllRequestsByType(String requestType) {
        try {
            return Optional.ofNullable(requestRepository.getAllRequestsByType(requestType));
        } catch (NullPointerException e) {
            System.out.println("Wrong request type.");
            return Optional.empty();
        }
    }

    public Optional<Request> getRequestById(Long requestId) {
        try {
            return Optional.ofNullable(requestRepository.getRequestById(requestId));
        } catch (InvalidRequestIdException e) {
            System.out.println("Request with ID " + requestId + " does not exist!");
            return Optional.empty();
        }
    }
}
