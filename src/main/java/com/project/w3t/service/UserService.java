package com.project.w3t.service;

import com.project.w3t.exceptions.BadRequest400.BadRequestException;
import com.project.w3t.exceptions.NotFound404.NotFoundException;
import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserDto;
import com.project.w3t.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        List<User> tempList = userRepository.findAll();
//        TODO proper exception and status code
        if (tempList.isEmpty()) throw new NotFoundException("Unable to process request - users list does not exist.");
        return tempList;
    }

    public void addUser(User user) {

//        TODO can only be user.setId(null).
        Optional<Long> testId = Optional.ofNullable(user.getId());
        if (testId.isPresent() && userRepository.existsById(testId.get())) user.setId(null);

//        TODO throw custom exc - email/user already taken, validation elsewhere (f.ex. email: string + @ + string + . + string).
        if (userRepository.existsByEmail(user.getEmail())) throw new BadRequestException("Unable to process request - email address is invalid.");
        if (userRepository.existsByUserId(user.getUserId())) throw new BadRequestException("Unable to process request - user Id is invalid.");
        userRepository.save(user);
    }

    public void updateUser(String userId, UserDto userDto) {
//        TODO implement
    }

    @Transactional
    public void deleteUser(String userId) {
//        TODO proper exception and status code
        if (!userRepository.existsByUserId(userId)) throw new NotFoundException("Unable to process request - user does not exist.");
        userRepository.deleteByUserId(userId);
    }

    public List<User> getAllUsersByManager(String managerId) {
        List<User> tempList = userRepository.findAllByManagerId(managerId);
//        TODO proper exception and status code
        if (tempList.isEmpty()) throw new NotFoundException("Unable to process request - users list does not exist.");
        return tempList;
    }

    public User getUserByUserId(String userId) {
        //        TODO proper exception and status code
        if (!userRepository.existsByUserId(userId)) throw new NotFoundException("Unable to process request - user does not exist.");
        return userRepository.findByUserId(userId);
    }
}