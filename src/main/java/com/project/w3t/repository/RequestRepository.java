package com.project.w3t.repository;

import com.project.w3t.exceptions.InvalidCommentLength;
import com.project.w3t.exceptions.InvalidDateRangeException;
import com.project.w3t.exceptions.InvalidRequestId;
import com.project.w3t.model.Request;
import com.project.w3t.model.RequestDto;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository {
    void addRequest(Request request) throws InvalidDateRangeException, InvalidCommentLength;

    void updateRequest(Long id, RequestDto requestDto) throws InvalidRequestId, InvalidCommentLength, InvalidDateRangeException;
}
