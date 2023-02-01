package com.project.w3t.service;

import com.project.w3t.exceptions.BadRequest400.BadRequestException;
import com.project.w3t.model.request.Request;
import com.project.w3t.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    private final static int COMMENT_MAX_LENGTH = 250;
    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> getAllRequests(){
        List<Request> tmpRequestList = requestRepository.findAll();

        if (tmpRequestList.isEmpty()) throw new BadRequestException("Could not find any requests.");
        return tmpRequestList;
    }

    public void addRequest(Request request) {
        if (request.getStartDate() == null || request.getEndDate() == null || !isRequestValid(request)) {
            throw new BadRequestException("Invalid date range.");
        }
        if (!isCommentLengthValid(request.getComment())) {
            throw new BadRequestException("Comment is too long.");
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
//
//    public void deleteRequest(Long requestId) throws InvalidRequestIdException {
//        requestRepository.deleteRequest(requestId);
//    }
//
//    public List<Request> getAllRequestsByType(String requestType) throws NullPointerException {
//        return requestRepository.getAllRequestsByType(requestType);
//    }
//


//    public List<Request> getAllRequestsByType(String requestType) throws NullPointerException {
//        return requestRepository.getAllRequestsByType(requestType);
//    }

    public Optional<Request> getRequestByRequestId(Long id)  {
        if (!requestRepository.existsById(id)) throw new BadRequestException("Request with this id does not exists.");

        return requestRepository.findById(id);
    }
}