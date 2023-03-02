package com.project.w3t.service;

import com.project.w3t.exceptions.BadRequest400.BadRequestException;
import com.project.w3t.exceptions.NotFound404.NotFoundException;
import com.project.w3t.model.user.RoleName;
import com.project.w3t.model.user.User;
import com.project.w3t.model.user.UserDto;
import com.project.w3t.model.user.UserType;
import com.project.w3t.repository.RoleRepository;
import com.project.w3t.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
//        TODO proper exception and status code
        if (userRepository.findAll().isEmpty())
            throw new NotFoundException("Unable to process request - users list does not exist.");
        return userRepository.findAll();
    }

    public void addUser(User user) {
//        TODO can only be user.setId(null).
        Optional<Long> testId = Optional.ofNullable(user.getId());
        if (testId.isPresent() && userRepository.existsById(testId.get())) user.setId(null);
//        TODO throw custom exc - email/user already taken, validation elsewhere (f.ex. email: string + @ + string + . + string).
        if (userRepository.existsByEmail(user.getEmail()))
            throw new BadRequestException("Unable to process request - email address is invalid.");
        if (userRepository.existsByUserId(user.getUserId()))
            throw new BadRequestException("Unable to process request - user Id is invalid.");

        assignUserRoles(user);

        userRepository.save(user);
    }
    public void updateUser(String userId, UserDto userDto) {
        User userToUpdate = getUserByUserId(userId);

        if (!isUserTypeValid(userDto.getUserType())) {
            throw new BadRequestException("Invalid user type.");
        }
        if (userDto.getManagerId() == null || userDto.getManagerId().isBlank()) {
            throw new BadRequestException("Manager id is missing");
        }
        if (userDto.getTeamId() == null || userDto.getTeamId().isBlank()) {
            throw new BadRequestException("Team id is missing");
        }
        userToUpdate.setUserType(userDto.getUserType());
        userToUpdate.setManagerId(userDto.getManagerId());
        userToUpdate.setTeamId(userDto.getTeamId());
        userRepository.save(userToUpdate);
    }

    private boolean isUserTypeValid(UserType userType) {
        return userType != null;
    }

    @Transactional
    public void deleteUser(String userId) {
//        TODO proper exception and status code
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
        if (!userRepository.existsByUserId(userId))
            throw new NotFoundException("Unable to process request - user does not exist.");
        return userRepository.findByUserId(userId);
    }

    public void assignUserRoles(User user) {

        switch (user.getUserType()) {
            case EMPLOYEE -> user.getRoles().add(roleRepository.findByRoleName(RoleName.ROLE_USER));
            case MANAGER -> user.getRoles().add(roleRepository.findByRoleName(RoleName.ROLE_MANAGER));
            case ADMIN -> user.getRoles().add(roleRepository.findByRoleName(RoleName.ROLE_ADMIN));
        }
    }
}