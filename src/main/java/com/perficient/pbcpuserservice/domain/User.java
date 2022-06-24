package com.perficient.pbcpuserservice.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author tyler.barton
 * @version 1.0, 6/23/2022
 * @project PBCP-UserService
 */
//@Table("users") // MySQL table name
@Getter
@Setter
@Document(collection = "users") // MongoDB collection name
public class User extends BaseEntity {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private EmailAddress[] emailAddress;
    private PhoneNumber[] phoneNumber;
}
