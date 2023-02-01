package com.project.w3t.repository;

import com.project.w3t.exceptions.InvalidEmailException;
import com.project.w3t.exceptions.InvalidUserIdException;
import com.project.w3t.exceptions.UserNotFoundException;
import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    public List<User> getAllUsers();
//
//    public void addUser(User user) throws InvalidEmailException, InvalidUserIdException;
//
//    public void updateUser(String userId, UserDto userDto);
//
//    public void deleteUser(String userId) throws UserNotFoundException;
//
//    public List<User> getAllUsersByManager(String managerId);
//
//    public User getUserByUserId(String userId) throws UserNotFoundException;

    boolean existsByEmail(String email);
    boolean existsByUserId(String userId);
    void deleteByUserId(String userId);
    User findByUserId(String userId);
    List<User> findAllByManagerId(String managerId);
}
