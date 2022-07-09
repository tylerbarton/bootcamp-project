package com.perficient.pbcpapptservice.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Contains appointment information for domain usage.
 *
 * @author tyler.barton
 * @version 1.0, 6/29/2022
 * @project PBCP-ApptService
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "appointments")
public class Appointment extends BaseEntity {

    // Required fields
    private String name;
    private String type;
    private String description;
    private Instant startTime;
    private Instant endTime;
    private Object metaData;

    // Additional fields
    private ApptStatus status;
}
