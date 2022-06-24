package com.perficient.pbcpuserservice.model;

import com.perficient.pbcpuserservice.domain.EmailAddress;
import com.perficient.pbcpuserservice.domain.PhoneNumber;

/**
 * @author tyler.barton
 * @version 1.0, 6/23/2022
 * @project PBCP-UserService
 */
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private EmailAddress[] emailAddress;
    private PhoneNumber[] phoneNumber;
}
