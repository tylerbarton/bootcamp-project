package com.perficient.pbcpapptservice.domain;

/**
 * Represents the state of an appointment.
 *
 * @author tyler.barton
 * @version 1.0, 6/29/2022
 * @project PBCP-ApptService
 */
public enum ApptStatus {
    PENDING,    // Indicates the client made the appointment, waiting for confirmation.
    SCHEDULED,  // The appointment has been confirmed by the handler.
    CANCELLED,  // The appointment has been cancelled.
    RESCHEDULED, // The appointment has been rescheduled, no link.
    COMPLETED   // The appointment has been completed.
}
