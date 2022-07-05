package com.perficient.pbcpuserservice.services;

import com.perficient.pbcpuserservice.model.UserDto;

import java.util.List;

/**
 * Business logic
 *
 * @author tyler.barton
 * @version 1.0, 6/23/2022
 * @project PBCP-UserService
 */
public interface UserService {
    UserDto getUserById(Long userId);

    List<UserDto> getUserByFirstName(String firstName);

    List<UserDto> getUserByLastName(String lastName);

    List<UserDto> getUserByFullName(String firstName, String lastName);

    UserDto createNewUser(UserDto userDto);

    UserDto updateUser(Long userId, UserDto userDto) throws Throwable;

    void deleteUser(Long userId, boolean hardDelete);

    List<UserDto> listUsers();

    boolean isUserDeleted(Long userId);
}
