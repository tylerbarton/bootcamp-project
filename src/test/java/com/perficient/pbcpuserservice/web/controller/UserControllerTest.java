package com.perficient.pbcpuserservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.pbcpuserservice.model.UserDto;
import com.perficient.pbcpuserservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * JUnit 5 unit testing of CRUD actions
 * @author tyler.barton
 */
@AutoConfigureMockMvc // this is required to use MockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // To fix mongo db connection issue
class UserControllerTest {
    private static String API_URL;

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @MockBean // Mock dependency
    UserService userService;

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
     * Create a list of dtos for mocking
     * @return a list of user dtos
     */
    private List<UserDto> getValidDtos() {
        List<UserDto> users = new ArrayList<>();
        users.add(getValidDto());
        return users;
    }

    @BeforeEach
    public void setUp() throws Throwable {
        API_URL = "http://localhost:8080/api/v1/user/";

        // Stub the dependencies
        when(userService.getUserById(anyLong())).thenReturn(getValidDto());
        when(userService.listUsers()).thenReturn(getValidDtos());
        when(userService.createNewUser(any(UserDto.class))).thenReturn(getValidDto());
        when(userService.updateUser(anyLong(), any(UserDto.class))).thenReturn(getValidDto());
    }

    @Test
    void createUser_CREATED() throws Exception {
        String dtoJson = objectMapper.writeValueAsString(getValidDto());

        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void getUser_By_Id() throws Exception {
        // Test
        mockMvc.perform(get(API_URL + anyLong()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(0)));
    }

    @Test
    void listUsers() {
    }

    @Test
    void deleteUser() {
    }


    @Test
    void updateUser() {
    }
}