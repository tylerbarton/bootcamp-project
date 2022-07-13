package com.perficient.pbcpapptservice.services;

import com.perficient.pbcpapptservice.domain.ApptStatus;
import com.perficient.pbcpapptservice.model.AppointmentDto;

import java.util.List;

/**
 * @author tyler.barton
 * @version 1.0, 6/29/2022
 * @project PBCP-ApptService
 */
public interface ApptService {
//     AppointmentDto getAppointment(String id);
//     AppointmentDto updateAppointment(String id, AppointmentDto appointmentDto);
//     void deleteAppointment(String id);


    List<AppointmentDto> getAppts();

    AppointmentDto getAppt(Long id);

    AppointmentDto createAppt(AppointmentDto appointmentDto);

    void updateAppt(Long id, AppointmentDto appointmentDto);

    void cancelAppt(Long id);

    void rescheduleAppt(Long id);

    void completeAppt(Long id);

    boolean exists(Long id);

    void hardDeleteAppt(Long id);

    void softDeleteAppt(Long id);

    ApptStatus getApptStatus(Long id);
}
