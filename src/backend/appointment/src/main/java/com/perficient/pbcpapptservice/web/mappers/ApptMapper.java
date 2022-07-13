package com.perficient.pbcpapptservice.web.mappers;

import com.perficient.pbcpapptservice.domain.Appointment;
import com.perficient.pbcpapptservice.model.AppointmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author tyler.barton
 * @version 1.0, 6/29/2022
 * @project PBCP-ApptService
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApptMapper {
    AppointmentDto toDto(Appointment appt);
    Appointment fromDto(AppointmentDto apptDto);
    List<AppointmentDto> toDtos(List<Appointment> appts);
}
