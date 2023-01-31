package com.project.w3t.service;

import com.project.w3t.exceptions.InvalidCommentLengthException;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.exceptions.RequestNotFoundException;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestDto;
import com.project.w3t.repository.RequestRepository;
import com.project.w3t.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public RequestService(RequestRepository requestRepository,
                          UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

//    public List<Request> getAllRequests() {
//        return requestRepository.getAllRequests();
//    }

//    public void addRequest(Request request) throws InvalidCommentLengthException {
//        requestRepository.addRequest(request);
//    }

//    public void updateRequest(Long id, RequestDto requestDto) throws InvalidCommentLengthException, InvalidRequestIdException {
//        requestRepository.updateRequest(id, requestDto);
//    }
//
//    public void deleteRequest(Long requestId) throws InvalidRequestIdException {
//        requestRepository.deleteRequest(requestId);
//    }
//
//    public List<Request> getAllRequestsByType(String requestType) throws NullPointerException {
//        return requestRepository.getAllRequestsByType(requestType);
//    }

//    public Optional<Request> getRequestById(Long requestId) {
//        try {
//            return Optional.ofNullable(requestRepository.getRequestById(requestId));
//        } catch (InvalidRequestIdException e) {
//            System.out.println("Request with ID " + requestId + " does not exist!");
//            return Optional.empty();
//        }
//    }
    public Request getRequestByRequestId(Long id) throws RequestNotFoundException {
        if (!requestRepository.existsById(id)) throw new RequestNotFoundException();
        return requestRepository.getById(id);
    }
    public List<Request> getAllRequests(){
        List<Request> tmpRequestList = requestRepository.findAll();

        if (tmpRequestList.isEmpty()) throw new RuntimeException();
        return tmpRequestList;
    }
}
