package com.perficient.pbcpuserservice.services;

import com.perficient.pbcpuserservice.domain.User;
import com.perficient.pbcpuserservice.model.UserDto;
import com.perficient.pbcpuserservice.repository.UserRepository;
import com.perficient.pbcpuserservice.web.controller.exceptions.NotFoundException;
import com.perficient.pbcpuserservice.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private final UserRepository userRepository;

    /**
     * Gets the user information from the repository
     * @param userId id of the user
     * @return user information
     */
    @Override
    public UserDto getUserById(Long userId) {
        List<User> users = userRepository.findAllByUserId(userId);
        if(users == null || users.isEmpty()){
            log.warn("Failed to retrieve user " + userId);
            throw new NotFoundException("User not found");
        }

        User user = users.get(0);
        log.debug("Successfully retrieved user " + user.getId());
        return userMapper.toDto(user);
    }

    /**
     * Saves a new user onto a database.
     * @param userDto input dto to save
     * @return populated userDto.
     */
    @Override
    public UserDto createNewUser(UserDto userDto) {
        if(userDto == null) {
            throw new NullArgumentException("userDto");
        }

        User user = userMapper.fromDto(userDto);
        return userMapper.toDto(userRepository.save(user));
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
        List<User> users = userRepository.findAllByUserId(userId);

        if(users == null || users.size() == 0) {
            throw new NotFoundException("User " + userId + " not found");
        }

        User user = userMapper.updateUser(users.get(0), userDto);
        return userMapper.toDto(userRepository.save(user));

    }

    /**
     * Delete user from the database
     * @param userId id of the user
     */
    @Override
    public void deleteUser(Long userId, boolean hardDelete) {
        if(hardDelete) {
            userRepository.deleteById(userId);
        } else {
            userRepository.softDeleteById(userId);
        }
    }

    /**
     * Get all users from the database
     * @return list of users
     */
    @Override
    public List<UserDto> listUsers() {
        return userMapper.toDtos(userRepository.findAll());
    }
}
