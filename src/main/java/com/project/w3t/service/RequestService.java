package com.project.w3t.service;

import com.project.w3t.exceptions.InvalidCommentLengthException;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.RequestNotFoundException;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestType;
import com.project.w3t.repository.RequestRepository;
import com.project.w3t.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    private final static int COMMENT_MAX_LENGTH = 250;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public RequestService(RequestRepository requestRepository,
                          UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    public List<Request> getAllRequests(){
        List<Request> tmpRequestList = requestRepository.findAll();

        if (tmpRequestList.isEmpty()) throw new RuntimeException();
        return tmpRequestList;
    }

    public void addRequest(Request request) throws InvalidCommentLengthException, InvalidDateRangeException {
        if (request.getStartDate() == null || request.getEndDate() == null || !isRequestValid(request)) {
            throw new InvalidDateRangeException();
        }
        if (!isCommentLengthValid(request.getComment())) {
            throw new InvalidCommentLengthException();
        }
        requestRepository.save(request);
    }

    private boolean isRequestValid(Request request) {
        List<Request> userRequestsFilteredByType = requestRepository.findAllByType(request.getType());
        return checkRange(userRequestsFilteredByType, request.getRequestDateRange()) && request.getEndDate().isAfter(request.getStartDate());
    }

    private boolean checkRange(List<Request> requests, List<LocalDate> dateRange) {
        for (LocalDate date : dateRange) {
            if (!checkDateAvailability(requests, date) || date.isBefore(LocalDate.now())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDateAvailability(List<Request> requests, LocalDate date) {
        return requests.stream().noneMatch(req -> req.getRequestDateRange().contains(date));
    }

    private boolean isCommentLengthValid(String comment) {
        return comment != null && comment.length() <= COMMENT_MAX_LENGTH;
    }

//    private boolean isRequestTypeValid(Request request){
//        for (RequestType requestType: RequestType.values()) {
//            if(requestType.getName().equals(request.getType().getName())){
//                return true;
//            }
//        }
//        return false;
//    }


//
//    public void updateRequest(Long id, RequestDto requestDto) throws InvalidCommentLengthException, InvalidRequestIdException {
//        requestRepository.updateRequest(id, requestDto);
//    }

    public void deleteRequest(Long requestId) throws RequestNotFoundException {
        if (!requestRepository.existsById(requestId)) {
            throw new RequestNotFoundException();
        }
        requestRepository.deleteById(requestId);
    }

    public List<Request> getAllRequestsByType(RequestType requestType) {
        return requestRepository.findAllByType(requestType);
    }

    public Optional<Request> getRequestByRequestId(Long id) throws RequestNotFoundException {
        if (!requestRepository.existsById(id)) throw new RequestNotFoundException();

        return requestRepository.findById(id);
    }
}
