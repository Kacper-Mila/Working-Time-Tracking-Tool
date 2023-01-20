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

    public void addRequest(Request request) throws InvalidDateRangeException, InvalidCommentLengthException {
            requestRepository.addRequest(request);
    }

    public void updateRequest(Long id, RequestDto requestDto) throws InvalidRequestIdException, InvalidDateRangeException, InvalidCommentLengthException {
            requestRepository.updateRequest(id, requestDto);
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
