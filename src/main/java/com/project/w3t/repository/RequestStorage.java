package com.project.w3t.repository;

import com.project.w3t.exceptions.InvalidCommentLength;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.InvalidRequestId;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.Request;
import com.project.w3t.model.Type;
import com.project.w3t.model.RequestDto;
import com.project.w3t.model.Status;
import com.project.w3t.service.RequestService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void addRequest(Request request) throws InvalidDateRangeException, InvalidCommentLength {
        if (!checkDateRange(request)) {
            if (checkCommentLength(request)) {
                userRequestList.add(request);
            } else {
                throw new InvalidCommentLength();
            }
        } else {
            throw new InvalidDateRangeException();
        }
    }

    public boolean checkDateRange(Request request) {
        return userRequestList.stream()
                .filter(req -> req.getType().equals(request.getType()))
                .anyMatch(req -> (req.getRequestDateRange().contains(request.getStartDate())
                        || (req.getRequestDateRange().contains(request.getEndDate()))));
    }

    public boolean checkCommentLength(Request request) {
        return request.getComment().length() <= COMMENT_MAX_LENGTH;
    }

    public void updateRequest(Long id, RequestDto requestDto) throws InvalidRequestId {
        Request requestToUpdate = userRequestList.stream()
                .filter(request -> request.getRequestId().equals(id))
                .findAny()
                .orElse(null);
        if (requestToUpdate == null) {
            throw new InvalidRequestId();
        }
        requestToUpdate.setType(requestDto.getType());
        requestToUpdate.setStartDate(requestDto.getStartDate());
        requestToUpdate.setEndDate(requestDto.getEndDate());
        requestToUpdate.setComment(requestDto.getComment());
        requestToUpdate.setStatus(Status.PENDING);
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

//    TODO frontend string switch to type if needed or onclick scroll list.
    public List<Request> getAllRequestsByType(Type requestType) {

//        Type requestType = null;
//        for (Type value : Type.values()) {
//            if (value.toString().equals(requestTypeString)) {
//                requestType = value;
//            }
//        }


        return switch (requestType) {
            case HOLIDAY -> userRequestList.stream().filter(request -> request.getType().equals(Type.HOLIDAY)).collect(Collectors.toList());
            case OVERTIME -> userRequestList.stream().filter(request -> request.getType().equals(Type.OVERTIME)).collect(Collectors.toList());
            case REMOTE ->userRequestList.stream().filter(request -> request.getType().equals(Type.REMOTE)).collect(Collectors.toList());
        };
    }

    public boolean checkRequestId(Long requestId){
        return userRequestList.stream().anyMatch(request -> request.getRequestId().equals(requestId));
    }
    public Request getRequestById(Long requestId) throws InvalidRequestIdException {
        if (checkRequestId(requestId)){
            return userRequestList.stream().filter(request -> request.getRequestId().equals(requestId)).findAny().get();
        }else {
            throw new InvalidRequestIdException();
        }
    }
}
