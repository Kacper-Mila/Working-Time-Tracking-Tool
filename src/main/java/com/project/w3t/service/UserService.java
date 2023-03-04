package com.project.w3t.service;

import com.project.w3t.exceptions.BadRequest400.BadRequestException;
import com.project.w3t.exceptions.NotFound404.NotFoundException;
import com.project.w3t.model.user.*;
import com.project.w3t.repository.RoleRepository;
import com.project.w3t.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    public static final String UNABLE_TO_PROCESS_REQUEST_USERS_LIST_DOES_NOT_EXIST = "Unable to process request - users list does not exist.";
    public static final String UNABLE_TO_PROCESS_REQUEST_USER_DOES_NOT_EXIST = "Unable to process request - user does not exist.";
    public static final String UNABLE_TO_PROCESS_REQUEST_EMAIL_ADDRESS_IS_INVALID = "Unable to process request - email address is invalid.";
    public static final String UNABLE_TO_PROCESS_REQUEST_USER_ID_IS_INVALID = "Unable to process request - user Id is invalid.";
    public static final String INVALID_USER_TYPE = "Invalid user type.";
    public static final String MANAGER_ID_IS_MISSING = "Manager id is missing";
    public static final String TEAM_ID_IS_MISSING = "Team id is missing";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_MANAGER = "ROLE_MANAGER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
//        TODO proper exception and status code
        if (userRepository.findAll().isEmpty())
            throw new NotFoundException(UNABLE_TO_PROCESS_REQUEST_USERS_LIST_DOES_NOT_EXIST);
        return userRepository.findAll();
    }

    public void addUser(User user) {
//        TODO can only be user.setId(null).
        Optional<Long> testId = Optional.ofNullable(user.getId());
        if (testId.isPresent() && userRepository.existsById(testId.get())) user.setId(null);
//        TODO throw custom exc - email/user already taken, validation elsewhere (f.ex. email: string + @ + string + . + string).
        if (userRepository.existsByEmail(user.getEmail()))
            throw new BadRequestException(UNABLE_TO_PROCESS_REQUEST_EMAIL_ADDRESS_IS_INVALID);
        if (userRepository.existsByUserId(user.getUserId()))
            throw new BadRequestException(UNABLE_TO_PROCESS_REQUEST_USER_ID_IS_INVALID);

        assignUserRoles(user);

        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    public void updateUser(String userId, UserDto userDto) {
        User userToUpdate = getUserByUserId(userId);

        if (!isUserTypeValid(userDto.getUserType())) {
            throw new BadRequestException(INVALID_USER_TYPE);
        }
        if (userDto.getManagerId() == null || userDto.getManagerId().isBlank()) {
            throw new BadRequestException(MANAGER_ID_IS_MISSING);
        }
        if (userDto.getTeamId() == null || userDto.getTeamId().isBlank()) {
            throw new BadRequestException(TEAM_ID_IS_MISSING);
        }
        userToUpdate.setUserType(userDto.getUserType());
        userToUpdate.setManagerId(userDto.getManagerId());
        userToUpdate.setTeamId(userDto.getTeamId());
        userToUpdate.setHolidays(userDto.getHolidays());
        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setFirstName(userDto.getFirstName());
        userToUpdate.setLastName(userDto.getLastName());
        userRepository.save(userToUpdate);
    }

    private boolean isUserTypeValid(UserType userType) {
        return userType != null;
    }

    @Transactional
    public void deleteUser(String userId) {
//        TODO proper exception and status code
        if (!userRepository.existsByUserId(userId))
            throw new NotFoundException(UNABLE_TO_PROCESS_REQUEST_USER_DOES_NOT_EXIST);
        userRepository.deleteByUserId(userId);
    }


    public List<User> getAllUsersByManager(String managerId) {
//        TODO proper exception and status code
        if (userRepository.findAllByManagerId(managerId).isEmpty()) {
            throw new NotFoundException(UNABLE_TO_PROCESS_REQUEST_USERS_LIST_DOES_NOT_EXIST);
        }
        return userRepository.findAllByManagerId(managerId);
    }

    public User getUserByUserId(String userId) {
        //        TODO proper exception and status code
        if (!userRepository.existsByUserId(userId))
            throw new NotFoundException(UNABLE_TO_PROCESS_REQUEST_USER_DOES_NOT_EXIST);
        return userRepository.findByUserId(userId).get();
    }

    public void assignUserRoles(User user) {
        user.setRoles(new ArrayList<>());
        switch (user.getUserType()) {
            case EMPLOYEE -> user.getRoles().add(roleRepository.findByName(ROLE_USER));
            case MANAGER -> user.getRoles().add(roleRepository.findByName(ROLE_MANAGER));
            case ADMIN -> user.getRoles().add(roleRepository.findByName(ROLE_ADMIN));
        }
        System.out.println(user.getRoles());
    }
}