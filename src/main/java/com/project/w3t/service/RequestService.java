package com.project.w3t.service;

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

    private Optional<Request> getRequestById(Long requestId){
        return Optional.ofNullable(requestRepository.getRequestById(requestId));
    }
}
