package com.project.w3t.service;

import com.project.w3t.exceptions.InvalidCommentLength;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.model.Request;
import com.project.w3t.repository.RequestRepository;
import org.springframework.stereotype.Service;

@Service
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.Request;
import com.project.w3t.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestService {
    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public void addRequest(Request request){
        try {
            requestRepository.addRequest(request);
        } catch (InvalidDateRangeException e) {
            System.out.println("Invalid date range!");
        } catch (InvalidCommentLength e) {
            System.out.println("Your comment is too long!");
        }

    }
    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    private Optional<Request> getRequestById(Long requestId) throws InvalidRequestIdException {
        return Optional.ofNullable(requestRepository.getRequestById(requestId));
    }
}
