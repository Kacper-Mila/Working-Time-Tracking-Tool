package com.project.w3t.repository;

import com.project.w3t.exceptions.InvalidEmailException;
import com.project.w3t.exceptions.InvalidUserIdException;
import com.project.w3t.exceptions.UserNotFoundException;
import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserStorage implements UserRepository {

    private List<User> userList = new ArrayList<>();


    @Override
    public List<User> getAllUsers() {
        return userList;
    }

    @Override
    public void addUser(User user) throws InvalidEmailException, InvalidUserIdException {
        if (!isEmailPresent(user)) {
            if (!isUserIdPresent(user)) {
                userList.add(user);
            } else {
                throw new InvalidUserIdException();
            }
        } else {
            throw new InvalidEmailException();
        }
    }

    //    TODO refactor with generics wildcard to merge checkers?
    private boolean isEmailPresent(User user) {
        Optional<User> tempUser = userList.stream()
                .filter(u -> u.getEmail().equals(user.getEmail()))
                .findFirst();
        if (tempUser.isPresent()) return true;
        else return false;
    }

    private boolean isUserIdPresent(User user) {
        Optional<User> tempUser = userList.stream()
                .filter(u -> u.getUserId().equals(user.getUserId()))
                .findFirst();
        if (tempUser.isPresent()) return true;
        else return false;
    }

    @Override
    public void updateUser(String userId, UserDto userDto) {
//TODO to implement.
    }

    @Override
    public void deleteUser(String userId) throws UserNotFoundException {
        Optional<User> userToDelete = Optional.ofNullable(getUserByUserId(userId));
        if (userToDelete.isPresent()) userList.remove(userToDelete.get());
//        else throw new UserNotFoundException();
    }

    @Override
    public List<User> getAllUsersByManager(String managerId) {
//        TODO to implement.
        return null;
    }

    @Override
    public User getUserByUserId(String userId) throws UserNotFoundException {
        Optional<User> tempUser = userList.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
        if (tempUser.isPresent()) return tempUser.get();
        else throw new UserNotFoundException();
    }
}
