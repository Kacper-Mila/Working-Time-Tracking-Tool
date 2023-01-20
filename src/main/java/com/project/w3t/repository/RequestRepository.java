package com.project.w3t.repository;

import com.project.w3t.exceptions.InvalidCommentLengthException;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.model.Request;
import com.project.w3t.model.RequestDto;
import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.Type;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository {
    List<Request> getAllRequests();

    void addRequest(Request request) throws InvalidDateRangeException, InvalidCommentLengthException;

    void updateRequest(Long id, RequestDto requestDto) throws InvalidRequestIdException, InvalidDateRangeException, InvalidCommentLengthException;

    void deleteRequest(Long requestId) throws InvalidRequestIdException;

    List<Request> getAllRequestsByType(String requestType);

    Request getRequestById(Long requestId) throws InvalidRequestIdException;
}
