package com.perficient.pbcpapptservice.services;

import com.perficient.pbcpapptservice.domain.Appointment;
import com.perficient.pbcpapptservice.domain.ApptStatus;
import com.perficient.pbcpapptservice.model.AppointmentDto;
import com.perficient.pbcpapptservice.repository.ApptRepository;
import com.perficient.pbcpapptservice.web.mappers.ApptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tyler.barton
 * @version 1.0, 6/29/2022
 * @project PBCP-ApptService
 */
@Service
@RequiredArgsConstructor
public class ApptServiceImpl implements ApptService {
    private final ApptMapper apptMapper;
    private final ApptRepository apptRepository;

    @Override
    public List<AppointmentDto> getAppts() {
        return apptMapper.toDtos(apptRepository.findAppointmentsByDeletedIsFalse());
    }

    @Override
    public AppointmentDto getAppt(Long id) {
        return apptMapper.toDto(apptRepository.findById(id).orElse(null));
    }

    @Override
    public AppointmentDto createAppt(AppointmentDto appointmentDto) {
        return apptMapper.toDto(apptRepository.save(apptMapper.fromDto(appointmentDto)));
    }

    @Override
    public void updateAppt(Long id, AppointmentDto appointmentDto) {
        apptRepository.save(apptMapper.fromDto(appointmentDto));
    }

    /**
     * Changes the status of the appointment to cancelled.
     * @param id the id of the appointment to cancel
     */
    @Override
    public void cancelAppt(Long id) {
        Appointment appt = apptRepository.findById(id).orElse(null);
        appt.setStatus(ApptStatus.CANCELLED);
        apptRepository.save(appt);
    }

    /**
     * Changes the status of the appointment to RESCHEDULED to indicate that the appointment has been rescheduled.
     * Assumes that the appointment has already been created and is in the database.
     * @param id
     */
    @Override
    public void rescheduleAppt(Long id) {
        Appointment appt = apptRepository.findById(id).orElse(null);
        appt.setStatus(ApptStatus.RESCHEDULED);
        apptRepository.save(appt);
    }

    @Override
    public void completeAppt(Long id) {
        Appointment appt = apptRepository.findById(id).orElse(null);
        appt.setStatus(ApptStatus.COMPLETED);
        apptRepository.save(appt);
    }

    /**
     * Checks if the appoint exists by id.
     * @param id the id of the appointment.
     * @return true if the appointment exists, else false.
     */
    @Override
    public boolean exists(Long id) {
        return apptRepository.existsByIdAndDeletedIsFalse(id);
    }

    /**
     * Removes the appointment from the database.
     * @param id the id of the appointment.
     */
    @Override
    public void hardDeleteAppt(Long id) {
        apptRepository.deleteById(id);
    }

    /**
     * Sets the deleted flag to true for an appointment by id.
     * @param id the id of the appointment.
     */
    @Override
    public void softDeleteAppt(Long id) {
        Appointment appt = apptRepository.findById(id).orElse(null);
        appt.setDeleted(true);
        apptRepository.save(appt);
    }

    /**
     * Gets the status of the appointment.
     * @param id the id of the appointment.
     * @return the status of the appointment.
     */
    @Override
    public ApptStatus getApptStatus(Long id) {
        return apptRepository.findById(id).orElse(null).getStatus();
    }
}
