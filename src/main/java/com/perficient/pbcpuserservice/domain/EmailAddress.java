package com.perficient.pbcpuserservice.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author tyler.barton
 * @version 1.0, 6/23/2022
 * @project PBCP-UserService
 */
@Builder
@Data
public class EmailAddress {
    public static final String EMAIL_ADDRESS_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

    private String address;
    private String type;
//    private boolean primary;
}
