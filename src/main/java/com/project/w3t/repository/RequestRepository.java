package com.project.w3t.repository;

import com.project.w3t.exceptions.InvalidRequestIdException;
import com.project.w3t.model.Request;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository {
    Request getRequestById(Long requestId) throws InvalidRequestIdException;
}
