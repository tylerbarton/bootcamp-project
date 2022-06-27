package com.perficient.pbcpuserservice.services;

import com.perficient.pbcpuserservice.domain.EmailAddress;
import com.perficient.pbcpuserservice.domain.PhoneNumber;
import com.perficient.pbcpuserservice.domain.User;
import com.perficient.pbcpuserservice.model.UserDto;
import com.perficient.pbcpuserservice.repository.UserRepository;
import com.perficient.pbcpuserservice.web.controller.exceptions.NotFoundException;
import com.perficient.pbcpuserservice.web.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Test class for UserServiceImpl to ensure that the service is working as expected.
 *
 * @author tyler.barton
 * @version 1.0, 6/23/2022
 * @project PBCP-UserService
 */
class UserServiceImplTest {

    private static final long USER_ID = 1L;

    @Mock // Simulate the behavior of a repository
    UserRepository userRepository;

    @Mock // Simulate the behavior of a mapper
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    User getValidUser(){
        User user = new User();
        user.setId(USER_ID);
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setAge(57);
        user.setGender("male");
        return user;
    }

    User getValidUserWithEmailAndPhone(){
        EmailAddress emailAddress = new EmailAddress("hello@world.com", "primary");
        PhoneNumber phoneNumber = new PhoneNumber("123-456-7890", "primary");
        User user = new User();
        user.setId(USER_ID);
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setAge(57);
        user.setGender("male");
        user.setPhoneNumber(new PhoneNumber[]{phoneNumber});
        user.setEmailAddress(new EmailAddress[]{emailAddress});
        return user;
    }

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        userMapper = Mappers.getMapper(UserMapper.class);
        userService = new UserServiceImpl(userMapper, userRepository);
    }

    @Test
    void getUserById_Should_Return_User() {
        // Arrange
        User user = getValidUser();
        user.setId(USER_ID);
        when(userRepository.findAllById(USER_ID)).thenReturn(new ArrayList<User>(){{add(user);}});
        // Act
        UserDto userDto = userService.getUserById(USER_ID);
        // Assert
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getAge(), userDto.getAge());
        assertEquals(user.getGender(), userDto.getGender());
    }

    @Test
    void getUserById_Should_Throw_NotFoundException() {
        // Arrange
        when(userRepository.findAllById(USER_ID)).thenReturn(new ArrayList<User>());
        // Act
        // Assert
        assertThrows(NotFoundException.class, () -> userService.getUserById(USER_ID));
    }

    @Test
    void getUserById_Should_Throw_NotFoundException_When_User_Is_Deleted() {
        // Arrange
        User user = getValidUser();
        user.setId(USER_ID);
        user.setDeleted(true);
        when(userRepository.findAllById(USER_ID)).thenReturn(new ArrayList<User>(){{add(user);}});
        // Act
        // Assert
        assertThrows(NotFoundException.class, () -> userService.getUserById(USER_ID));
    }

    @Test
    void createNewUser_Should_Create_User() {
        // Arrange
        User user = getValidUser();
        UserDto userDto = userMapper.toDto(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        // Act
        UserDto newUserDto = userService.createNewUser(userDto);
        // Assert
        assertEquals(user.getId(), newUserDto.getId());
        assertEquals(user.getFirstName(), newUserDto.getFirstName());
        assertEquals(user.getLastName(), newUserDto.getLastName());
        assertEquals(user.getAge(), newUserDto.getAge());
        assertEquals(user.getGender(), newUserDto.getGender());
    }

    @Test
    void createNewUser_Should_Create_User_With_Email_And_Phone(){
        // Arrange
        User user = getValidUserWithEmailAndPhone();
        UserDto userDto = userMapper.toDto(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        // Act
        UserDto newUserDto = userService.createNewUser(userDto);
        // Assert
        assertEquals(user.getId(), newUserDto.getId());
        assertEquals(user.getFirstName(), newUserDto.getFirstName());
        assertEquals(user.getLastName(), newUserDto.getLastName());
        assertEquals(user.getAge(), newUserDto.getAge());
        assertEquals(user.getGender(), newUserDto.getGender());
        assertEquals(user.getEmailAddress()[0].getAddress(), newUserDto.getEmailAddress()[0].getAddress());
        assertEquals(user.getPhoneNumber()[0].getNumber(), newUserDto.getPhoneNumber()[0].getNumber());
    }

    @Test
    void updateUser_Should_Update_User() throws Throwable {
        // Arrange
        User user = getValidUser();
        UserDto userDto = userMapper.toDto(user);
        when(userRepository.findAllById(USER_ID)).thenReturn(new ArrayList<User>(){{add(user);}});
        when(userRepository.save(any(User.class))).thenReturn(user);
        // Act
        UserDto updatedUserDto = userService.updateUser(userDto.getId(), userDto);
        // Assert
        assertEquals(user.getId(), updatedUserDto.getId());
        assertEquals(user.getFirstName(), updatedUserDto.getFirstName());
        assertEquals(user.getLastName(), updatedUserDto.getLastName());
        assertEquals(user.getAge(), updatedUserDto.getAge());
        assertEquals(user.getGender(), updatedUserDto.getGender());
    }

    @Test
    void updateUser_Should_Throw_Exception_If_User_Does_Not_Exist() throws Throwable {
        // Arrange
        User user = getValidUser();
        UserDto userDto = userMapper.toDto(user);
        when(userRepository.findAllById(USER_ID)).thenReturn(new ArrayList<User>());
        // Act
        // Assert
        assertThrows(NotFoundException.class, () -> userService.updateUser(userDto.getId(), userDto));

    }

    @Test
    void deleteUser_Soft_Should_Update_User() {
        // Arrange
        User user = getValidUser();
        UserDto userDto = userMapper.toDto(user);
        when(userRepository.findAllById(USER_ID)).thenReturn(new ArrayList<User>(){{add(user);}});

        User savedUser = getValidUser();
        savedUser.setDeleted(true);
        when(userRepository.save(any(User.class))).thenReturn(user);
        // Act
        userService.deleteUser(userDto.getId(), false);
        // Assert
        assertEquals(user.getId(), userDto.getId());
        assertTrue(user.isDeleted());
    }

    @Test
    void listUsers_Should_Return_Users() {
        // Arrange
        User user = getValidUser();
        UserDto userDto = userMapper.toDto(user);
        when(userRepository.findAllAndDeletedIsFalse()).thenReturn(new ArrayList<User>(){{add(user);}});
        // Act
        ArrayList<UserDto> userDtos = (ArrayList<UserDto>) userService.listUsers();
        // Assert
        assertEquals(user.getId(), userDtos.get(0).getId());
        assertEquals(user.getFirstName(), userDtos.get(0).getFirstName());
        assertEquals(user.getLastName(), userDtos.get(0).getLastName());
        assertEquals(user.getAge(), userDtos.get(0).getAge());
        assertEquals(user.getGender(), userDtos.get(0).getGender());
    }

    @Test
    void listUsers_Should_Return_Empty_List_If_No_Users_Exist() {
        // Arrange
        when(userRepository.findAllAndDeletedIsFalse()).thenReturn(new ArrayList<User>());
        // Act
        ArrayList<UserDto> userDtos = (ArrayList<UserDto>) userService.listUsers();
        // Assert
        assertTrue(userDtos.isEmpty());
    }
}