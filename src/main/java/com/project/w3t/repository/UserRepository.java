package com.project.w3t.repository;

import com.project.w3t.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUserId(String userId);
    void deleteByUserId(String userId);
    User findByUserId(String userId);
    List<User> findAllByManagerId(String managerId);
}