package com.project.w3t.repository;

import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByType(RequestType requestType);
    List<Request> findAllByOwnerId(String ownerId);
}