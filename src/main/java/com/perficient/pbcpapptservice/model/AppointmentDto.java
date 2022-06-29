package com.perficient.pbcpapptservice.model;

/**
 * Dto for Appointment used by the controller.
 *
 * @author tyler.barton
 * @version 1.0, 6/29/2022
 * @project PBCP-ApptService
 */
public class AppointmentDto {
    private String name;
    private String type;
    private String description;
    private String startTime;
    private String endTime;
    private Object metaData;
}
