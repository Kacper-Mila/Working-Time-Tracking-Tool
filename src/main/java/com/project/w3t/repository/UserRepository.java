package com.project.w3t.repository;

import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    public List<User> getAllUsers();

    public void addUser(User user);

    public void updateUser(String userId, UserDto userDto);

    public void deleteUser(String userId);

    public List<User> getAllUsersByManager(String managerId);

    public Object getUserById(String userId);
}
