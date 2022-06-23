package com.perficient.pbcpuserservice.domain;

/**
 * @author tyler.barton
 * @version 1.0, 6/23/2022
 * @project PBCP-UserService
 */
public class PhoneNumber {
    public static final String PHONE_NUMBER_REGEX = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$";

    private String number;
    private String type;
}
