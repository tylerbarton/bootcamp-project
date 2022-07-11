package com.perficient.pbcpuserservice.web.mappers;

import com.perficient.pbcpuserservice.domain.User;
import com.perficient.pbcpuserservice.model.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapperTest {

    @Mock
    private UserMapper userMapper;

    /**
     * @return a constructed user entity
     */
    User getValidUser(){
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setAge(57);
        user.setGender("male");
        return user;
    }

    /**
     * @return a constructed user dto
     */
    UserDto getValidDto(){
        return UserDto.builder()
                .firstName("John")
                .lastName("Smith")
                .age(57)
                .gender("male")
                .emailAddress(null)
                .phoneNumber(null)
                .build();
    }

    /**
     * Mock the mapper
     */
    @BeforeEach
    public void setup(){
        userMapper = Mappers.getMapper(UserMapper.class);
        assertNotNull(userMapper);
    }

    @Test
    void toDto() {
        User user = getValidUser();
        UserDto dto = userMapper.toDto(user);
        assertNotNull(dto);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getFirstName(), dto.getFirstName());
        assertEquals(user.getLastName(), dto.getLastName());
    }

    @Test
    void fromDto() {
        UserDto dto = getValidDto();
        User user = userMapper.fromDto(dto);
        assertNotNull(user);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getFirstName(), dto.getFirstName());
        assertEquals(user.getLastName(), dto.getLastName());
    }

    @Test
    void toDtos() {
        // Arrange
        User user1 = getValidUser();
        User user2 = getValidUser();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        // Act
        List<UserDto> dtos = userMapper.toDtos(users);
        // Assert
        assertEquals(2, dtos.size());
        assertEquals(user1.getId(), dtos.get(0).getId());
        assertEquals(user1.getFirstName(), dtos.get(0).getFirstName());
        assertEquals(user1.getLastName(), dtos.get(0).getLastName());
        assertEquals(user2.getId(), dtos.get(1).getId());
        assertEquals(user2.getFirstName(), dtos.get(1).getFirstName());
        assertEquals(user2.getLastName(), dtos.get(1).getLastName());

    }

    @Test
    void fromDtos() {
        // Arrange
        List<UserDto> dtos = new ArrayList<>();
        dtos.add(getValidDto());
        dtos.add(getValidDto());
        dtos.add(getValidDto());
        // Act
        List<User> users = userMapper.fromDtos(dtos);
        // Assert
        assertEquals(3, users.size());
        assertEquals(users.get(0).getId(), dtos.get(0).getId());
        assertEquals(users.get(0).getFirstName(), dtos.get(0).getFirstName());
        assertEquals(users.get(0).getLastName(), dtos.get(0).getLastName());
    }

    @Test
    void updateUser() {
        // Arrange
        User user = new User();
        UserDto dto = getValidDto();
        // Act
        user = userMapper.updateUser(user, dto);
        // Assert
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getFirstName(), dto.getFirstName());
        assertEquals(user.getLastName(), dto.getLastName());
        assertEquals(user.getAge(), dto.getAge());
        assertEquals(user.getGender(), dto.getGender());
    }
}