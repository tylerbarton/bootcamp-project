package com.perficient.pbcpclient.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tyler.barton
 * @version 1.0, 6/28/2022
 * @project PBCP-Client
 */
@Data
public class UserDto implements Serializable {
    private long userId;
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private String[] emailAddresses;
    private String[] phoneNumbers;
}