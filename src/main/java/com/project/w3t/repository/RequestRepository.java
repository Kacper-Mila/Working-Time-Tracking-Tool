package com.project.w3t.repository;

import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    //    List<Request> getAllRequests();
//
//    void addRequest(Request request) throws InvalidDateRangeException, InvalidCommentLengthException;
//
//    void updateRequest(Long id, RequestDto requestDto) throws InvalidRequestIdException, InvalidDateRangeException, InvalidCommentLengthException;
//
//    void deleteRequest(Long requestId) throws InvalidRequestIdException;
//
//    List<Request> getAllRequestsByType(String requestType);
//
//    Request getRequestById(Long requestId) throws InvalidRequestIdException;
    List<Request> findAllByType(RequestType requestType);
}
