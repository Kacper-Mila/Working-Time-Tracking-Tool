package com.project.w3t.service;

import com.project.w3t.exceptions.InvalidCommentLengthException;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.repository.RequestRepository;
import org.springframework.stereotype.Service;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        } catch (InvalidCommentLengthException e) {
            System.out.println("Your comment is too long!");
        }
    }

    public void updateRequest(Long id, RequestDto requestDto) {
        try {
            requestRepository.updateRequest(id, requestDto);
        } catch (InvalidRequestIdException e) {
            System.out.println("Invalid request Id!");
        } catch (InvalidCommentLengthException e) {
            System.out.println("Your comment is too long!");
        } catch (InvalidDateRangeException e) {
            System.out.println("Invalid date range!");
        }
    }

    public void deleteRequest(Long requestId) throws InvalidRequestIdException {
            requestRepository.deleteRequest(requestId);
    }

    public List<Request> getAllRequestsByType(String requestType) throws NullPointerException {
            return requestRepository.getAllRequestsByType(requestType);
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
