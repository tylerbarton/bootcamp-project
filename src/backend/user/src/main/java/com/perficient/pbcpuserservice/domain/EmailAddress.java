package com.perficient.pbcpuserservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class EmailAddress implements Serializable {
    public static final String EMAIL_ADDRESS_REGEX = "^[a-zA-Z0-9.!#$%&'*+=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$|^$";

    @Pattern(regexp = EMAIL_ADDRESS_REGEX)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String address;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String type;
//    private boolean primary;
}
