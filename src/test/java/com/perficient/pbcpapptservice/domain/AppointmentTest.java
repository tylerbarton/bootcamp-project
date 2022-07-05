package com.perficient.pbcpapptservice.domain;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {
    @Test
    void getId(){
        Appointment appt = new Appointment();
        appt.setId(1L);
        assertEquals(1L, appt.getId());
    }

    @Test
    void getName() {
        Appointment appt = new Appointment();
        appt.setName("Appointment 1");
        assertEquals("Appointment 1", appt.getName());
    }

    @Test
    void getType() {
        Appointment appt = new Appointment();
        appt.setType("Checkup");
        assertEquals("Checkup", appt.getType());
    }

    @Test
    void getDescription() {
        Appointment appt = new Appointment();
        appt.setDescription("Description 1");
        assertEquals("Description 1", appt.getDescription());
    }

    @Test
    void getStartTime() {
        Appointment appt = new Appointment();
        ZonedDateTime startTime = ZonedDateTime.now();
        appt.setStartTime(startTime);
        assertEquals(startTime, appt.getStartTime());
    }

    @Test
    void getEndTime() {
        Appointment appt = new Appointment();
        appt.setEndTime(ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZonedDateTime.now().getZone()));
        assertEquals(ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZonedDateTime.now().getZone()), appt.getEndTime());
    }

    @Test
    void getMetaData() {
        Appointment appt = new Appointment();
        appt.setMetaData(null);
        assertNull(appt.getMetaData());
    }

    @Test
    void getStatus() {
        Appointment appt = new Appointment();
        appt.setStatus(ApptStatus.SCHEDULED);
        assertEquals(ApptStatus.SCHEDULED, appt.getStatus());
    }

    @Test
    void setName() {
        Appointment appt = new Appointment();
        appt.setName("Appointment 1");
        assertEquals("Appointment 1", appt.getName());
    }

    @Test
    void setType() {
        Appointment appt = new Appointment();
        appt.setType("Checkup");
        assertEquals("Checkup", appt.getType());
    }

    @Test
    void setDescription() {
        Appointment appt = new Appointment();
        appt.setDescription("Description 1");
        assertEquals("Description 1", appt.getDescription());
    }

    @Test
    void setStartTime() {
        Appointment appt = new Appointment();
        ZonedDateTime startTime = ZonedDateTime.now();
        appt.setStartTime(startTime);
        assertEquals(startTime, appt.getStartTime());
    }

    @Test
    void setEndTime() {
        Appointment appt = new Appointment();
        appt.setEndTime(ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZonedDateTime.now().getZone()));
        assertEquals(ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZonedDateTime.now().getZone()), appt.getEndTime());
    }

    @Test
    void setMetaData() {
        Appointment appt = new Appointment();
        appt.setMetaData(null);
        assertNull(appt.getMetaData());
    }

    @Test
    void setStatus() {
        Appointment appt = new Appointment();
        appt.setStatus(ApptStatus.SCHEDULED);
        assertEquals(ApptStatus.SCHEDULED, appt.getStatus());
    }
}