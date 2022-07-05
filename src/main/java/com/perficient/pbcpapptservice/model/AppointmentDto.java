package com.perficient.pbcpapptservice.model;

import lombok.*;

import java.time.ZonedDateTime;

/**
 * Dto for Appointment used by the controller.
 *
 * @author tyler.barton
 * @version 1.0, 6/29/2022
 * @project PBCP-ApptService
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDto {
    private String name;
    private String type;
    private String description;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private Object metaData;
}
