package com.project.w3t.repository;

import com.project.w3t.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUserId(String userId);
    boolean existsByManagerId(String managerId);
    void deleteByUserId(String userId);
    Optional<User> findByUserId(String userId);
    User findByManagerId(String managerId);
    List<User> findAllByManagerId(String managerId);
}