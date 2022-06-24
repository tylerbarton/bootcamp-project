package com.perficient.pbcpuserservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author tyler.barton
 * @version 1.0, 6/23/2022
 * @project PBCP-UserService
 */
//@Table("users") // MySQL table name
@Document("users") // MongoDb document name
public class User extends BaseEntity {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private EmailAddress[] emailAddress;
    private PhoneNumber[] phoneNumber;
}
