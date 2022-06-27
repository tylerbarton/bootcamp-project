package com.perficient.pbcpuserservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author tyler.barton
 * @version 1.0, 6/23/2022
 * @project PBCP-UserService
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber implements Serializable {
    public static final String PHONE_NUMBER_REGEX = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$";

    @Pattern(regexp = PHONE_NUMBER_REGEX)
    private String number;
    private String type;
}
