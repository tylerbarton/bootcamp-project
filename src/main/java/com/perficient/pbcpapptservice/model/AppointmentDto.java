package com.perficient.pbcpapptservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.perficient.pbcpapptservice.web.mappers.TimeInstantDeserializer;
import com.perficient.pbcpapptservice.web.mappers.TimeInstantSerializer;
import lombok.*;

import java.time.Instant;

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
    private Long id;
    private String name;
    private String type;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @JsonDeserialize(using = TimeInstantDeserializer.class)
    @JsonSerialize(using = TimeInstantSerializer.class)
    private Instant startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @JsonDeserialize(using = TimeInstantDeserializer.class)
    @JsonSerialize(using = TimeInstantSerializer.class)
    private Instant endTime;
    private Object metaData;
}
