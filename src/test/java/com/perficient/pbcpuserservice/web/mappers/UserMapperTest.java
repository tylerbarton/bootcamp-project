package com.perficient.pbcpuserservice.web.mappers;

import com.perficient.pbcpuserservice.domain.User;
import com.perficient.pbcpuserservice.model.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;

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

    }

    @Test
    void fromDtos() {
    }

    @Test
    void updateUser() {

    }
}