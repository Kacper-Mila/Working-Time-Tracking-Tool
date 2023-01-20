package com.project.w3t.repository;

import com.project.w3t.exceptions.InvalidCommentLengthException;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestType;
import com.project.w3t.model.request.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Predicate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RequestStorage implements RequestRepository {

    private List<Request> userRequestList = new ArrayList<>();
    private static int COMMENT_MAX_LENGTH = 250;

    @Override
    public List<Request> getAllRequests() {
        return userRequestList;
    }

    //    TODO hours for overtime and remote, validate type..
    public void addRequest(Request request) throws InvalidDateRangeException, InvalidCommentLengthException {
        if (checkRequest(request)) {
            if (checkCommentLength(request.getComment())) {
                userRequestList.add(request);
            } else {
                throw new InvalidCommentLengthException();
            }
        } else {
            throw new InvalidDateRangeException();
        }
    }

    private boolean checkRequest(Request request) {
        return checkRange(getRequestsToCheckDateRange(request), request.getRequestDateRange());
    }

    private boolean checkRange(List<Request> requests, List<LocalDate> dateRange) {
        for (LocalDate date : dateRange) {
            if (!checkDateAvailability(requests, date)) {
                return false;
            }
        }
        return true;
    }

    private List<Request> getRequestsToCheckDateRange(Request request) {
        return userRequestList.stream()
                .filter(Predicate.not(req -> req.getRequestId().equals(request.getRequestId())))
                .filter(req -> req.getType().equals(request.getType())).toList();
    }

    private boolean checkDateAvailability(List<Request> requests, LocalDate date) {
        return requests.stream().noneMatch(req -> req.getRequestDateRange().contains(date));
    }

    private boolean checkCommentLength(String comment) {
        return comment.length() <= COMMENT_MAX_LENGTH;
    }

    //    TODO validate type on first when same date on both change.
    public Request getRequestToUpdate(Long id) {
        return userRequestList.stream()
                .filter(request -> request.getRequestId().equals(id))
                .findAny()
                .orElse(null);
    }


    public void updateRequest(Long id, RequestDto requestDto) throws InvalidRequestIdException, InvalidDateRangeException, InvalidCommentLengthException {
        Request requestToUpdate = getRequestToUpdate(id);
        if (requestToUpdate == null) {
            throw new InvalidRequestIdException();
        }
        List<LocalDate> dateRange = DateRange.getDateRange(requestDto.getStartDate(), requestDto.getEndDate());
        String comment = requestDto.getComment();

        if (!checkRange(getRequestsToCheckDateRange(requestToUpdate), dateRange)) {
            throw new InvalidDateRangeException();
        }

        if (!isCommentLengthValid(comment)) {
            throw new InvalidCommentLengthException();
        }

        requestToUpdate.setType(requestDto.getType());
        requestToUpdate.setStartDate(requestDto.getStartDate());
        requestToUpdate.setEndDate(requestDto.getEndDate());
        requestToUpdate.setComment(requestDto.getComment());
        requestToUpdate.setStatus(Status.PENDING);
    }

    @Override
    public void deleteRequest(Long requestId) throws InvalidRequestIdException {
        Request requestToDelete = userRequestList.stream()
                .filter(request -> request.getRequestId().equals(requestId))
                .findAny()
                .orElse(null);
        if (requestToDelete == null) {
            throw new InvalidRequestIdException();
        }
        userRequestList.remove(requestToDelete);
    }

//    TODO frontend string to enum switch, onclick scroll list,
//     collective get by body elements driven in request logic,
//     exceptions.
    public List<Request> getAllRequestsByType(String requestTypeString) throws NullPointerException{

        RequestType requestType = null;
        for (RequestType value : RequestType.values()) {
            if (value.toString().equals(requestTypeString.toUpperCase())) {
                requestType = value;
            }
        }

        return switch (requestType) {
            case HOLIDAY ->
                    userRequestList.stream().filter(request -> request.getType().equals(RequestType.HOLIDAY)).collect(Collectors.toList());
            case OVERTIME ->
                    userRequestList.stream().filter(request -> request.getType().equals(RequestType.OVERTIME)).collect(Collectors.toList());
            case REMOTE ->
                    userRequestList.stream().filter(request -> request.getType().equals(RequestType.REMOTE)).collect(Collectors.toList());
        };
    }

    public Request getRequestById(Long requestId) throws InvalidRequestIdException {
        if (checkRequestId(requestId)) {
            return userRequestList.stream().filter(request -> request.getRequestId().equals(requestId)).findAny().get();
        } else {
            throw new InvalidRequestIdException();
        }
    }

    private boolean checkRequestId(Long requestId) {
        return userRequestList.stream().anyMatch(request -> request.getRequestId().equals(requestId));
    }
}
