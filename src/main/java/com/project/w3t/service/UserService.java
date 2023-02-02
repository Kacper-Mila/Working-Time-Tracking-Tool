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
//        TODO proper exception and status code
        if (userRepository.findAll().isEmpty())
            throw new NotFoundException("Unable to process request - users list does not exist.");
        return userRepository.findAll();
    }

    public void addUser(User user) {
//        TODO check if needed, proper exception and status code
        if (user == null) throw new BadRequestException("Unable to process request - user data is invalid.");

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
        if (userId == null) throw new BadRequestException("Unable to process request - user Id is invalid.");
        if (!userRepository.existsByUserId(userId))
            throw new NotFoundException("Unable to process request - user does not exist.");
        userRepository.deleteByUserId(userId);
    }

    public List<User> getAllUsersByManager(String managerId) {
//        TODO proper exception and status code
        if (userRepository.findAllByManagerId(managerId).isEmpty()) {
            throw new NotFoundException("Unable to process request - users list does not exist.");
        }
        return userRepository.findAllByManagerId(managerId);
    }

    public User getUserByUserId(String userId) {
        //        TODO proper exception and status code
        if (userId == null) throw new BadRequestException("Unable to process request - user Id is invalid.");
        if (!userRepository.existsByUserId(userId))
            throw new NotFoundException("Unable to process request - user does not exist.");
        return userRepository.findByUserId(userId);
    }
}