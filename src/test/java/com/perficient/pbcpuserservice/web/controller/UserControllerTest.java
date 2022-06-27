package com.perficient.pbcpuserservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.pbcpuserservice.model.UserDto;
import com.perficient.pbcpuserservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * JUnit 5 unit testing of CRUD actions
 * @author tyler.barton
 */
@WebMvcTest(UserController.class) // Only intended to test controllers
class UserControllerTest {
    private static String API_URL = "/api/v1/user";

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
                .id(0L)
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

    /**
     * Sets the property for the dynamic controller mapping url.
     * @param registry the dynamic property registry
     */
    @DynamicPropertySource
    static void controllerProperties(DynamicPropertyRegistry registry) {
        registry.add("service.api.path", () -> API_URL);
    }

    /**
     * Called before each test
     * @throws Throwable if an error occurs
     */
    @BeforeEach
    public void setUp() throws Throwable {
    }


    @Test
    void createUser_CREATED() throws Exception {
        UserDto dto = getValidDto();
        when(userService.createNewUser(dto)).thenReturn(dto);
        String dtoJson = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getUser_By_Id_OK() throws Exception {
        UserDto dto = getValidDto();
        when(userService.getUserById(anyLong())).thenReturn(dto);

        mockMvc.perform(get("/api/v1/user/0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(0)));
    }

    @Test
    void listUsers_OK() throws Exception {
        List<UserDto> savedDtos = new ArrayList<>();
        savedDtos.add(getValidDto());
        savedDtos.add(getValidDto());
        savedDtos.add(getValidDto());
        when(userService.listUsers()).thenReturn(savedDtos);

        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteUser_NOCONTENT() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(getValidDto());
        mockMvc.perform(delete("/api/v1/user/0"))
                .andExpect(status().isNoContent());
    }


    @Test
    void updateUser_OK() throws Throwable {
        UserDto dto = getValidDto();
        when(userService.updateUser(anyLong(), any(UserDto.class))).thenReturn(dto);
        String dtoJson = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/api/v1/user/" + dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isOk());

        then(userService).should().updateUser(anyLong(), any(UserDto.class));
    }


    @Test
    void getUser_By_Id_NOT_FOUND() throws Exception {
        mockMvc.perform(get("/api/v1/user/0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void listUsers_NOT_FOUND() throws Exception {
        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_NOT_FOUND() throws Exception {
        mockMvc.perform(delete("/api/v1/user/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_NOT_FOUND() throws Exception {
        UserDto dto = getValidDto();
        String dtoJson = objectMapper.writeValueAsString(dto);
        mockMvc.perform(put("/api/v1/user/" + dto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser_BAD_REQUEST() throws Exception {
        UserDto dto = getValidDto();
        String dtoJson = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isBadRequest());
    }
}