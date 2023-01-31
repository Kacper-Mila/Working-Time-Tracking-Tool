package com.project.w3t.service;

import com.project.w3t.exceptions.InvalidCommentLengthException;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.exceptions.RequestNotFoundException;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestDateRange;
import com.project.w3t.model.request.RequestDto;
import com.project.w3t.model.request.RequestStatus;
import com.project.w3t.model.request.RequestType;
import com.project.w3t.repository.RequestRepository;
import com.project.w3t.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public List<Request> getAllRequests() {
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
        return checkRange(userRequestsFilteredByType, request.getRequestDateRange());
    }

    private boolean checkRange(List<Request> requests, List<LocalDate> dateRange) {
        if (!isStartDateBeforeEndDate(dateRange)) {
            throw new InvalidDateRangeException();
        }
        for (LocalDate date : dateRange) {
            if (!checkDateAvailability(requests, date) || date.isBefore(LocalDate.now())) {
                return false;
            }
        }
        return true;
    }

    private boolean isStartDateBeforeEndDate(List<LocalDate> dateRange) {
        return dateRange.get(0).isBefore(dateRange.get(dateRange.size() - 1));

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
    public void updateRequest(Long id, RequestDto requestDto) throws InvalidCommentLengthException, InvalidRequestIdException, RequestNotFoundException {
        Optional<Request> request = getRequestByRequestId(id);
        Request requestToUpdate = request.get();
        List<LocalDate> dateRange = RequestDateRange.getDateRange(requestDto.getStartDate(), requestDto.getEndDate());
        String comment = requestDto.getComment();

        if (requestDto.getStartDate() == null || requestDto.getEndDate() == null || !checkRange(getRequestsToCheckDateRange(requestToUpdate), dateRange)) {
            throw new InvalidDateRangeException();
        }

        if (!isCommentLengthValid(comment)) {
            throw new InvalidCommentLengthException();
        }

        requestToUpdate.setType(requestDto.getType());
        requestToUpdate.setStartDate(requestDto.getStartDate());
        requestToUpdate.setEndDate(requestDto.getEndDate());
        requestToUpdate.setComment(requestDto.getComment());
        requestToUpdate.setRegistrationDate(LocalDate.now());
        requestToUpdate.setStatus(RequestStatus.PENDING);

        requestRepository.save(requestToUpdate);
    }

    private List<Request> getRequestsToCheckDateRange(Request request) {
        return getAllRequestsByType(request.getType()).stream()
                .filter(Predicate.not(req -> req.getRequestId().equals(request.getRequestId())))
                .collect(Collectors.toList());
    }

    //
//    public void deleteRequest(Long requestId) throws InvalidRequestIdException {
//        requestRepository.deleteRequest(requestId);
//    }
//
    public List<Request> getAllRequestsByType(RequestType requestType) throws NullPointerException {
        return requestRepository.findAllByType(requestType);
    }
//

    public Optional<Request> getRequestByRequestId(Long id) throws RequestNotFoundException {
        if (!requestRepository.existsById(id)) throw new RequestNotFoundException();

        return requestRepository.findById(id);
    }
}
