package com.perficient.pbcpapptservice.model;

import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;

/**
 * Dto for Appointment used by the controller.
 *
 * @author tyler.barton
 * @version 1.0, 6/29/2022
 * @project PBCP-ApptService
 */
@Getter
@Setter
public class AppointmentDto {
    private String name;
    private String type;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime;
    private Object metaData;
}
