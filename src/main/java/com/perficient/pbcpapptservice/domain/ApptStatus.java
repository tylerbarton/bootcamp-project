package com.perficient.pbcpapptservice.domain;

/**
 * Represents the state of an appointment.
 *
 * @author tyler.barton
 * @version 1.0, 6/29/2022
 * @project PBCP-ApptService
 */
public enum ApptStatus {
    PENDING,
    CONFIRMED,
    CANCELLED,
    RESCHEDULED,
    COMPLETED
}
