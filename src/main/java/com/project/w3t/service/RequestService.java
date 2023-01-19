package com.project.w3t.service;

import com.project.w3t.exceptions.InvalidCommentLength;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.InvalidRequestId;
import com.project.w3t.model.Request;
import com.project.w3t.repository.RequestRepository;
import org.springframework.stereotype.Service;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.RequestDto;
import com.project.w3t.model.Type;

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

    public void addRequest(Request request) {
        try {
            requestRepository.addRequest(request);
        } catch (InvalidDateRangeException e) {
            System.out.println("Invalid date range!");
        } catch (InvalidCommentLength e) {
            System.out.println("Your comment is too long!");
        }
    }

    public void updateRequest(Long id, RequestDto requestDto) {
        try {
            requestRepository.updateRequest(id, requestDto);
        } catch (InvalidRequestId e) {
            System.out.println("Invalid request Id!");
        } catch (InvalidCommentLength e) {
            System.out.println("Your comment is too long!");
        } catch (InvalidDateRangeException e) {
            System.out.println("Invalid date range!");
        }
    }

    public void deleteRequest(Long requestId) {
        try {
            requestRepository.deleteRequest(requestId);
        } catch (InvalidRequestId e) {
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
