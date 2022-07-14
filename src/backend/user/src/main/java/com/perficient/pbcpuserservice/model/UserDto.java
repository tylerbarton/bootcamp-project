package com.perficient.pbcpuserservice.model;

import com.perficient.pbcpuserservice.domain.EmailAddress;
import com.perficient.pbcpuserservice.domain.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author tyler.barton
 * @version 1.0, 6/23/2022
 * @project PBCP-UserService
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {
    private String id;
    @NotBlank
    @Size(max=45)
    private String firstName;
    @NotBlank
    @Size(max=45)
    private String lastName;
    @NotBlank
    private String gender;
    @PositiveOrZero
    private int age;
    @Valid // Required to validate the object
    private EmailAddress[] emailAddress;
    @Valid
    private PhoneNumber[] phoneNumber;
}
