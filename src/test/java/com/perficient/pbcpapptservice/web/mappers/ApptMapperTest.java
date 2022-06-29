package com.perficient.pbcpapptservice.web.mappers;

import com.perficient.pbcpapptservice.domain.Appointment;
import com.perficient.pbcpapptservice.model.AppointmentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApptMapperTest {

    @Mock
    private ApptMapper apptMapper;

    @BeforeEach
    public void setup(){
        // Required to ensure apptMapper is not null
        apptMapper = Mappers.getMapper(ApptMapper.class);
        assertNotNull(apptMapper);
    }


    /**
     * @return a constructed appointment dto
     */
    AppointmentDto getValidDto() {
        AppointmentDto dto = new AppointmentDto();
        dto.setName("Appointment 1");
        dto.setType("Checkup");
        dto.setDescription("Description 1");
//        dto.setStartTime(ZonedDateTime.now());
//        dto.setEndTime(ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZonedDateTime.now().getZone()));
//        dto.setMetaData(null);
        return dto;
    }

    Appointment getValidAppt() {
        Appointment appt = new Appointment();
        appt.setName("Appointment 1");
        appt.setType("Checkup");
        appt.setDescription("Description 1");
//        appt.setStartTime(ZonedDateTime.now());
//        appt.setEndTime(ZonedDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZonedDateTime.now().getZone()));
//        appt.setMetaData(null);
        return appt;
    }

    @Test
    void toDto() {
        Appointment appt = getValidAppt();
        AppointmentDto dto = apptMapper.toDto(appt);
        assertNotNull(dto);
        assertEquals(appt.getName(), dto.getName());
        assertEquals(appt.getType(), dto.getType());
        assertEquals(appt.getDescription(), dto.getDescription());
//        assertEquals(appt.getStartTime(), dto.getStartTime());
//        assertEquals(appt.getEndTime(), dto.getEndTime());
//        assertEquals(appt.getMetaData(), dto.getMetaData());

    }

    @Test
    void fromDto() {
        AppointmentDto dto = getValidDto();
        Appointment appt = apptMapper.fromDto(dto);
        assertNotNull(appt);
        assertEquals(dto.getName(), appt.getName());
        assertEquals(dto.getType(), appt.getType());
        assertEquals(dto.getDescription(), appt.getDescription());
//        assertEquals(dto.getStartTime(), appt.getStartTime());
//        assertEquals(dto.getEndTime(), appt.getEndTime());
//        assertEquals(dto.getMetaData(), appt.getMetaData());
    }

    @Test
    void toDtos() {
        List<Appointment> appts = List.of(getValidAppt(), getValidAppt());
        List<AppointmentDto> dtos = apptMapper.toDtos(appts);
        assertNotNull(dtos);
        assertEquals(appts.size(), dtos.size());
        assertEquals(appts.get(0).getName(), dtos.get(0).getName());
        assertEquals(appts.get(0).getType(), dtos.get(0).getType());
        assertEquals(appts.get(0).getDescription(), dtos.get(0).getDescription());
//        assertEquals(appts.get(0).getStartTime(), dtos.get(0).getStartTime());
//        assertEquals(appts.get(0).getEndTime(), dtos.get(0).getEndTime());
//        assertEquals(appts.get(0).getMetaData(), dtos.get(0).getMetaData());
    }
}