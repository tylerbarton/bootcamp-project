package com.perficient.pbcpapptservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.pbcpapptservice.model.AppointmentDto;
import com.perficient.pbcpapptservice.services.ApptService;
import com.perficient.pbcpapptservice.services.ApptServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * JUnit 5 unit testing of CRUD actions for Appointment Controller
 * @author tyler.barton
 * @implNote Uses JSONResultMatchers to test JSON responses
 */
@WebMvcTest(ApptController.class)
class ApptControllerTest {
    private static String API_URL = "/api/v1/appt";
    private static long APPT_ID = 1L;

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @MockBean // Mock dependency
    ApptService apptService;

    AppointmentDto getValidDto() {
        return AppointmentDto.builder()
                .name("John Smith")
                .type("Checkup")
                .startTime(ZonedDateTime.of(2020, 1, 1, 12, 0, 0, 0, ZoneId.of("UTC")))
                .endTime(ZonedDateTime.of(2020, 1, 1, 13, 0, 0, 0, ZoneId.systemDefault()))
                .description("Test Appointment")
                .build();
    }

    List<AppointmentDto> getValidDtoList() {
        List<AppointmentDto> dtos = new ArrayList<>();
        dtos.add(getValidDto());
        dtos.add(getValidDto());
        return dtos;
    }

    @Test
    void getAppts_Should_Return_Appointments() throws Exception {
        // Arrange
        when(apptService.getAppts()).thenReturn(getValidDtoList());
        // Act
        mockMvc.perform(get(API_URL + "/all"))
                // Assert
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.containsString("John Smith")))
                .andExpect(jsonPath("$[0].type",  Matchers.containsString("Checkup")))
                .andExpect(jsonPath("$[0].startTime",  Matchers.containsString("2020-01-01T12:00:00Z")))
                .andExpect(jsonPath("$[0].endTime",  Matchers.containsString("2020-01-01T13:00:00-06:00")))
                .andExpect(jsonPath("$[0].description",  Matchers.containsString("Test Appointment")));
    }

    @Test
    void getAppts_Should_Return_Not_Found() throws Exception {
        // Arrange
        when(apptService.getAppts()).thenReturn(new ArrayList<>());
        // Act
        mockMvc.perform(get(API_URL + "/all"))
                // Assert
                .andExpect(status().isNotFound());
    }

    @Test
    void getAppt_Should_Return_Appointment() throws Exception {
        // Arrange
        when(apptService.getAppt(APPT_ID)).thenReturn(getValidDto());
        // Act
        mockMvc.perform(get(API_URL + "/appts/"+APPT_ID))
                // Assert
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Matchers.containsString("John Smith")))
                .andExpect(jsonPath("$.type",  Matchers.containsString("Checkup")))
                .andExpect(jsonPath("$.startTime",  Matchers.containsString("2020-01-01T12:00:00Z")))
                .andExpect(jsonPath("$.endTime",  Matchers.containsString("2020-01-01T13:00:00-06:00")))
                .andExpect(jsonPath("$.description",  Matchers.containsString("Test Appointment")));
    }

    @Test
    void getAppt_Should_Return_Not_Found() throws Exception {
        // Arrange
        when(apptService.getAppt(APPT_ID)).thenReturn(null);
        // Act
        mockMvc.perform(get(API_URL + "/appts/"+APPT_ID))
                // Assert
                .andExpect(status().isNotFound());
    }

    @Test
    void createAppt() throws Exception {
        // Arrange
        AppointmentDto dto = getValidDto();
        when(apptService.createAppt(ArgumentMatchers.any())).thenReturn(dto);

        // Act
        mockMvc.perform(post(API_URL + "/appts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                // Assert
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Matchers.containsString("John Smith")))
                .andExpect(jsonPath("$.type",  Matchers.containsString("Checkup")))
                .andExpect(jsonPath("$.startTime",  Matchers.containsString("2020-01-01T12:00:00Z")))
                .andExpect(jsonPath("$.endTime",  Matchers.containsString("2020-01-01T13:00:00-06:00")))
                .andExpect(jsonPath("$.description",  Matchers.containsString("Test Appointment")));
    }

    @Test
    void createAppt_Should_Return_Bad_Request() throws Exception {
        // Arrange
        AppointmentDto dto = getValidDto();
        when(apptService.createAppt(dto)).thenReturn(null);

        // Act
        mockMvc.perform(post(API_URL + "/appts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                // Assert
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateAppt_Should_Update_Appointment() throws Exception {
        // Arrange
        AppointmentDto dto = getValidDto();
        when(apptService.exists(APPT_ID)).thenReturn(true);
        // Act
        mockMvc.perform(put(API_URL + "/appts/"+APPT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                // Assert
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAppt_Should_Delete_Entry() throws Exception {
        // Arrange
        when(apptService.exists(APPT_ID)).thenReturn(true);
        // Act
        mockMvc.perform(delete(API_URL + "/appts/"+APPT_ID))
                // Assert
                .andExpect(status().isNoContent());
    }

    @Test
    void cancelAppt() throws Exception {
        // Arrange
        when(apptService.exists(APPT_ID)).thenReturn(true);
        // Act
        mockMvc.perform(put(API_URL + "/appts/"+APPT_ID+"/cancel"))
                // Assert
                .andExpect(status().isNoContent());
    }

    @Test
    void cancelAppt_Should_Return_Not_Found() throws Exception {
        // Arrange
        when(apptService.exists(APPT_ID)).thenReturn(false);
        // Act
        mockMvc.perform(put(API_URL + "/appts/"+APPT_ID+"/cancel"))
                // Assert
                .andExpect(status().isNotFound());
    }

    @Test
    void rescheduleAppt() throws Exception {
        // Arrange
        when(apptService.exists(APPT_ID)).thenReturn(true);
        doNothing().when(apptService).rescheduleAppt(APPT_ID);
        // Act
        mockMvc.perform(put(API_URL + "/appts/"+APPT_ID+"/reschedule"))
                // Assert
                .andExpect(status().isNoContent());
    }

    @Test
    void completeAppt() throws Exception {
        // Arrange
        when(apptService.exists(anyLong())).thenReturn(true);

        doNothing().when(apptService).completeAppt(APPT_ID);

        // Act
        mockMvc.perform(put(API_URL + "/appts/"+APPT_ID+"/complete"))
                // Assert
                .andExpect(status().isNoContent());
    }

    @Test
    void completeAppt_Should_Return_Not_Found() throws Exception {
        // Arrange
        when(apptService.exists(anyLong())).thenReturn(false);

        // Act
        mockMvc.perform(put(API_URL + "/appts/"+APPT_ID+"/complete"))
                // Assert
                .andExpect(status().isNotFound());
    }

    @Test
    void rescheduleAppt_Should_Return_Not_Found() throws Exception {
        // Arrange
        when(apptService.exists(APPT_ID)).thenReturn(false);
        // Act
        mockMvc.perform(put(API_URL + "/appts/"+APPT_ID+"/reschedule"))
                // Assert
                .andExpect(status().isNotFound());
    }
}