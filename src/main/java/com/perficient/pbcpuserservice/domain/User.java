package com.perficient.pbcpuserservice.domain;

/**
 * @author tyler.barton
 * @version 1.0, 6/23/2022
 * @project PBCP-UserService
 */
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private EmailAddress[] emailAddress;
    private PhoneNumber[] phoneNumber;
}
