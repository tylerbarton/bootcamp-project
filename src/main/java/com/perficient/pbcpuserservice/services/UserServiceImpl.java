package com.perficient.pbcpuserservice.services;

import com.perficient.pbcpuserservice.domain.User;
import com.perficient.pbcpuserservice.model.UserDto;
import com.perficient.pbcpuserservice.repository.UserRepository;
import com.perficient.pbcpuserservice.web.controller.exceptions.NotFoundException;
import com.perficient.pbcpuserservice.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.NullArgumentException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implements the user service logic
 *
 * @author tyler.barton
 * @version 1.0, 6/23/2022
 * @project PBCP-UserService
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    /**
     * Gets the user information from the repository
     * @param userId id of the user
     * @return user information
     */
    @Override
    public UserDto getUserById(Long userId) {
        List<User> users = userRepository.findAllById(userId);
        if(users == null || users.isEmpty()){
            log.warn("Failed to retrieve user " + userId);
            throw new NotFoundException("User not found");
        }

        User user = users.get(0);
        if(user.isDeleted()){
            log.warn("Attempted to retrieve deleted user " + userId);
            throw new NotFoundException("User not found");
        }

        log.debug("Successfully retrieved user " + user.getId());
        return userMapper.toDto(user);
    }

    /**
     * Saves a new user onto a database.
     * @param userDto input dto to save
     * @return populated userDto.
     */
    @Transactional
    @Override
    public UserDto createNewUser(UserDto userDto) {
        if(userDto == null) {
            throw new NullArgumentException("userDto");
        }

        User user = userMapper.fromDto(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    /**
     * Update user information in the database
     * @param userId id of the user
     * @param userDto input dto to update
     * @return newly updated dto
     */
    @Override
    public UserDto updateUser(Long userId, UserDto userDto) throws Throwable {
        if(userDto == null) {
            throw new NullArgumentException("userDto");
        }
        List<User> users = userRepository.findAllById(userId);

        if(users == null || users.size() == 0) {
            throw new NotFoundException("User " + userId + " not found");
        }

        User user = userMapper.updateUser(users.get(0), userDto);
        return userMapper.toDto(userRepository.save(user));

    }

    /**
     * Delete user from the database either hard or soft.
     * @param userId id of the user
     * @param hardDelete if true, delete user from the database. If false, set user to inactive.
     */
    @Override
    public void deleteUser(Long userId, boolean hardDelete) {
        if(hardDelete) {
            hardDeleteById(userId);
        } else {
            softDeleteById(userId);
        }
    }

    /**
     * Changes the user status to deleted
     * @param userId id of the user
     */
    private void softDeleteById(Long userId) {
        List<User> users = userRepository.findAllById(userId);
        if(users == null || users.isEmpty()) {
            throw new NotFoundException("User " + userId + " not found");
        }
        userRepository.findAllById(userId).forEach(user -> {
            user.setDeleted(true);
            userRepository.save(user);
        });

//        userRepository.findById(userId).ifPresent(user -> {
//            user.setDeleted(true);
//            userRepository.save(user);
//        });
    }

    /**
     * Hard deletes the user from the database
     */
    private void hardDeleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Get all users from the database
     * @return list of users
     */
    @Override
    public List<UserDto> listUsers() {
        return userMapper.toDtos(userRepository.findAllAndDeletedIsFalse());
    }

    /**
     * Check if user is deleted
     * @param userId id of the user
     * @return true if user is deleted
     */
    @Override
    public boolean isUserDeleted(Long userId) {
        List<User> users = userRepository.findAllByIdAndDeleted(userId, true);
        if(users == null || users.isEmpty()){
            return false;
        }
        User user = users.get(0);
        return true;
    }
}
