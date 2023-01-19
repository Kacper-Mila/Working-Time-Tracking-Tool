package com.project.w3t.repository;

import com.project.w3t.exceptions.InvalidCommentLength;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.InvalidRequestId;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.DateRange;
import com.project.w3t.model.Request;
import com.project.w3t.model.Type;
import com.project.w3t.model.RequestDto;
import com.project.w3t.model.Status;
import com.project.w3t.service.RequestService;
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

    //    TODO hours for overtime and remote
    public void addRequest(Request request) throws InvalidDateRangeException, InvalidCommentLength {
        if (checkRequest(request)) {
            if (checkCommentLength(request.getComment())) {
                userRequestList.add(request);
            } else {
                throw new InvalidCommentLength();
            }
        } else {
            throw new InvalidDateRangeException();
        }
    }

    private List<Request> getRequestsToCheckDateRange(Request request) {
        return userRequestList.stream()
                .filter(Predicate.not(req -> req.getRequestId().equals(request.getRequestId())))
                .filter(req -> req.getType().equals(request.getType())).toList();
    }

    private boolean checkRequest(Request request) {
        return checkRange(getRequestsToCheckDateRange(request), request.getRequestDateRange());
    }

    private boolean checkDateAvailability(List<Request> requests, LocalDate date) {
        return requests.stream().noneMatch(req -> req.getRequestDateRange().contains(date));
    }

    private boolean checkRange(List<Request> requests, List<LocalDate> dateRange) {
        for (LocalDate date : dateRange) {
            if (!checkDateAvailability(requests, date)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkCommentLength(String comment) {
        return comment.length() <= COMMENT_MAX_LENGTH;
    }

    public void updateRequest(Long id, RequestDto requestDto)
            throws InvalidRequestId, InvalidCommentLength, InvalidDateRangeException {
        Request requestToUpdate = userRequestList.stream()
                .filter(request -> request.getRequestId().equals(id))
                .findAny()
                .orElse(null);
        if (requestToUpdate == null) {
            throw new InvalidRequestId();
        }
        List<LocalDate> dateRange = DateRange.getDateRange(requestDto.getStartDate(), requestDto.getEndDate());
        String comment = requestDto.getComment();
        if (!checkRange(getRequestsToCheckDateRange(requestToUpdate), dateRange)) {
            throw new InvalidDateRangeException();
        } else {
            if (!checkCommentLength(comment)) {
                throw new InvalidCommentLength();
            }
            requestToUpdate.setType(requestDto.getType());
            requestToUpdate.setStartDate(requestDto.getStartDate());
            requestToUpdate.setEndDate(requestDto.getEndDate());
            requestToUpdate.setComment(requestDto.getComment());
            requestToUpdate.setStatus(Status.PENDING);
        }
    }

    @Override
    public void deleteRequest(Long requestId) throws InvalidRequestId {
        Request requestToDelete = userRequestList.stream()
                .filter(request -> request.getRequestId().equals(requestId))
                .findAny()
                .orElse(null);
        if (requestToDelete == null) {
            throw new InvalidRequestId();
        }
        userRequestList.remove(requestToDelete);
    }

    //    TODO frontend string to enum switch, onclick scroll list,
//     collective get by body elements driven in request logic,
//     exceptions.
    public List<Request> getAllRequestsByType(String requestTypeString) {

        Type requestType = null;
        for (Type value : Type.values()) {
            if (value.toString().equals(requestTypeString.toUpperCase())) {
                requestType = value;
            }
        }

        return switch (requestType) {
            case HOLIDAY ->
                    userRequestList.stream().filter(request -> request.getType().equals(Type.HOLIDAY)).collect(Collectors.toList());
            case OVERTIME ->
                    userRequestList.stream().filter(request -> request.getType().equals(Type.OVERTIME)).collect(Collectors.toList());
            case REMOTE ->
                    userRequestList.stream().filter(request -> request.getType().equals(Type.REMOTE)).collect(Collectors.toList());
        };
    }

    public Request getRequestById(Long requestId) throws InvalidRequestIdException {
        if (checkRequestId(requestId)) {
            return userRequestList.stream().filter(request -> request.getRequestId().equals(requestId)).findAny().get();
        } else {
            throw new InvalidRequestIdException();
        }
    }

    public boolean checkRequestId(Long requestId) {
        return userRequestList.stream().anyMatch(request -> request.getRequestId().equals(requestId));
    }
}
