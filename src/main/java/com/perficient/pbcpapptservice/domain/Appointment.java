package com.perficient.pbcpapptservice.domain;

/**
 * Contains appointment information
 *
 * @author tyler.barton
 * @version 1.0, 6/29/2022
 * @project PBCP-ApptService
 */
public class Appointment extends BaseEntity {

    private String name;
    private String type;
    private String description;
    private String startTime;
    private String endTime;
    private Object metaData;
}
