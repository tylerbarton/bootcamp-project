package com.perficient.pbcpapptservice.domain;

import lombok.Data;
import lombok.Getter;

import java.security.Timestamp;

/**
 * Contains appointment information for domain usage.
 *
 * @author tyler.barton
 * @version 1.0, 6/29/2022
 * @project PBCP-ApptService
 */
@Data
public class Appointment extends BaseEntity {

    // Required fields
    private String name;
    private String type;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime;
    private Object metaData;

    // Additional fields
    private ApptStatus status;
}
