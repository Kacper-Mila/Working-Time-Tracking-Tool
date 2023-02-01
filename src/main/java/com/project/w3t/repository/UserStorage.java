//package com.project.w3t.repository;
//
//import com.project.w3t.exceptions.InvalidEmailException;
//import com.project.w3t.exceptions.InvalidUserIdException;
//import com.project.w3t.exceptions.UserNotFoundException;
//import com.project.w3t.model.user.User;
//import com.project.w3t.model.user.UserDto;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Component
//public class UserStorage{
//
//    private List<User> userList = new ArrayList<>();
//    @Override
//    public void updateUser(String userId, UserDto userDto) {
////TODO to implement.
//    }
//
//    @Override
//    public void deleteUser(String userId) throws UserNotFoundException {
//        Optional<User> userToDelete = Optional.ofNullable(getUserByUserId(userId));
//        if (userToDelete.isPresent()) userList.remove(userToDelete.get());
////        else throw new UserNotFoundException();
//    }
//
//    @Override
//    public List<User> getAllUsersByManager(String managerId) {
////        TODO to implement.
//        return null;
//    }
//
//    @Override
//    public User getUserByUserId(String userId) throws UserNotFoundException {
//        Optional<User> tempUser = userList.stream()
//                .filter(user -> user.getUserId().equals(userId))
//                .findFirst();
//        if (tempUser.isPresent()) return tempUser.get();
//        else throw new UserNotFoundException();
//    }
//}
