package com.perficient.pbcpapptservice.services;

import com.perficient.pbcpapptservice.domain.Appointment;
import com.perficient.pbcpapptservice.domain.ApptStatus;
import com.perficient.pbcpapptservice.model.AppointmentDto;
import com.perficient.pbcpapptservice.repository.ApptRepository;
import com.perficient.pbcpapptservice.web.mappers.ApptMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Uses the Arrange-Act-Assert pattern to test the ApptServiceImpl class.
 *
 * @author tyler.barton
 * @version 1.0, 6/29/2022
 * @project PBCP-ApptService
 */
class ApptServiceImplTest {

    private static final long APPT_ID = 1L;

    @Mock // Simulate the behavior of a repository
    ApptRepository repository;

    @Mock // Simulate the behavior of a mapper
    ApptMapper mapper;

    @InjectMocks // Inject the mocks into the service
    ApptServiceImpl apptService;



    AppointmentDto getValidDto() {
        AppointmentDto dto = new AppointmentDto();
        dto.setName("Appointment 1");
        dto.setType("Checkup");
        dto.setDescription("Description 1");
        dto.setStartTime(ZonedDateTime.now());
        dto.setEndTime(ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZonedDateTime.now().getZone()));
        dto.setMetaData(null);
        return dto;
    }

    Appointment getValidAppt() {
        Appointment appt = mapper.fromDto(getValidDto());
        appt.setId(APPT_ID);
        appt.setStatus(ApptStatus.SCHEDULED);
        appt.setDeleted(false);
        return appt;
    }

    /**
     * Called before each test to setup the mocks
     */
    @BeforeEach
    public void setup(){
        // Fixes the null pointer exception when calling the mapper and repository
        MockitoAnnotations.openMocks(this);
        // Required to ensure apptMapper is not null
        mapper = Mappers.getMapper(ApptMapper.class);
        assertNotNull(mapper);
        // Required to mock the methods
        apptService = new ApptServiceImpl(mapper, repository);

    }

    @Test
    void getAppts_ShouldReturnAllAppointments() {
        // Arrange
        Appointment appt = getValidAppt();
        appt.setId(APPT_ID);
        when(repository.findAppointmentsByDeletedIsFalse()).thenReturn(new ArrayList<Appointment>(){{add(appt);}});
        // Act
        List<AppointmentDto> apptDtos = apptService.getAppts();
        // Assert
        assertEquals(1, apptDtos.size());
        assertEquals(appt.getName(), apptDtos.get(0).getName());


    }

    @Test
    void getAppt_ShouldGetAppointment() {
        // Arrange
        when(repository.findById(APPT_ID)).thenReturn(java.util.Optional.of(getValidAppt()));
        // Act
        AppointmentDto dto = apptService.getAppt(APPT_ID);
        // Assert
        assertNotNull(dto);
        assertEquals("Appointment 1", dto.getName());
    }

    @Test
    void getAppt_ShouldReturnNull() {
        // Arrange
        when(repository.findById(APPT_ID)).thenReturn(java.util.Optional.empty());
        // Act
        AppointmentDto dto = apptService.getAppt(APPT_ID);
        // Assert
        assertNull(dto);
    }

    @Test
    void createAppt_ShouldCreateAppointment() {
        // Arrange
        AppointmentDto dto = getValidDto();
        Appointment appt = mapper.fromDto(dto);
        when(repository.save(any(Appointment.class))).thenReturn(appt);
        // Act
        AppointmentDto result = apptService.createAppt(dto);
        // Assert
        assertNotNull(result);
    }

    @Test
    void updateAppt_ShouldChangeFieldsInRepository() {
        // Arrange
        AppointmentDto dto = getValidDto();
        Appointment appt = mapper.fromDto(dto);
        when(repository.findById(APPT_ID)).thenReturn(java.util.Optional.of(appt));
        when(repository.save(any(Appointment.class))).thenReturn(appt);
        // Act
        apptService.updateAppt(APPT_ID, dto);
        // Assert
        appt = repository.findById(APPT_ID).get();
        assertEquals(appt.getName(), dto.getName());
        assertEquals(appt.getDescription(), dto.getDescription());
        assertEquals(appt.getEndTime(), dto.getEndTime());
        assertEquals(appt.getStartTime(), dto.getStartTime());
    }

    @Test
    void cancelAppt() {
        // Arrange
        Appointment mock = Mockito.mock(Appointment.class);
        when(mock.getStatus()).thenReturn(ApptStatus.CANCELLED);
        when(repository.findById(APPT_ID)).thenReturn(java.util.Optional.of(mock));
        when(repository.save(any(Appointment.class))).thenReturn(mock);
        // Act
        apptService.cancelAppt(APPT_ID);
        // Assert
        assertTrue(apptService.getApptStatus(APPT_ID).equals(ApptStatus.CANCELLED));
    }

    @Test
    void rescheduleAppt() {
        // Arrange
        Appointment mock = Mockito.mock(Appointment.class);
        when(mock.getStatus()).thenReturn(ApptStatus.RESCHEDULED);
        when(repository.findById(APPT_ID)).thenReturn(java.util.Optional.of(mock));
        when(repository.save(any(Appointment.class))).thenReturn(mock);
        // Act
        apptService.rescheduleAppt(APPT_ID);
        // Assert
        assertTrue(apptService.getApptStatus(APPT_ID).equals(ApptStatus.RESCHEDULED));
    }

    @Test
    void completeAppt() {
        // Arrange
        Appointment mock = Mockito.mock(Appointment.class);
        when(mock.getStatus()).thenReturn(ApptStatus.COMPLETED);
        when(repository.findById(APPT_ID)).thenReturn(java.util.Optional.of(mock));
        when(repository.save(any(Appointment.class))).thenReturn(mock);
        // Act
        apptService.completeAppt(APPT_ID);
        // Assert
        assertTrue(apptService.getApptStatus(APPT_ID).equals(ApptStatus.COMPLETED));
    }

    @Test
    void exists() {
        // Arrange
        when(repository.existsByIdAndDeletedIsFalse(APPT_ID)).thenReturn(true);
        // Act
        boolean result = apptService.exists(APPT_ID);
        // Assert
        assertTrue(result);
    }

    @Test
    void hardDeleteAppt() {
        // Arrange
        Appointment deleted = getValidAppt();
        deleted.setDeleted(true);
        when(repository.findById(APPT_ID)).thenReturn(java.util.Optional.of(deleted));
        when(repository.save(any(Appointment.class))).thenReturn(deleted);
        // Act
        apptService.hardDeleteAppt(APPT_ID);
        // Assert
//        assertTrue(deleted.getDeleted());
    }

    @Test
    void softDeleteAppt() {
        // Arrange
        Appointment deleted = getValidAppt();
        deleted.setDeleted(true);
        when(repository.findById(APPT_ID)).thenReturn(java.util.Optional.of(deleted));
        when(repository.save(any(Appointment.class))).thenReturn(deleted);
        // Act
        apptService.softDeleteAppt(APPT_ID);
        // Assert
        assertTrue(deleted.isDeleted());
    }

    @Test
    void getAppts() {
    }

    @Test
    void getAppt() {
    }

    @Test
    void createAppt() {
    }

    @Test
    void updateAppt() {
    }

    @Test
    void getApptStatus() {
        // Arrange
        Appointment mock = Mockito.mock(Appointment.class);
        when(mock.getStatus()).thenReturn(ApptStatus.PENDING);
        when(repository.findById(APPT_ID)).thenReturn(java.util.Optional.of(mock));
        // Act
        ApptStatus status = apptService.getApptStatus(APPT_ID);
        // Assert
        assertEquals(ApptStatus.PENDING, status);
    }

//    @Test
//    public void givenSavedAction_TimeIsRetrievedCorrectly() {
//        String id = "testId";
//        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
//
//        actionRepository.save(new Action(id, "click-action", now));
//        Action savedAction = actionRepository.findById(id).get();
//
//        Assert.assertEquals(now.withNano(0), savedAction.getTime().withNano(0));
//    }
}