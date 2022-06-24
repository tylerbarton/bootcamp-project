package com.perficient.pbcpuserservice.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author tyler.barton
 * @version 1.0, 6/23/2022
 * @project PBCP-UserService
 */
//@Table("users") // MySQL table name
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "${spring.data.mongodb.collection}") // MongoDB collection name
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private EmailAddress[] emailAddress;
    private PhoneNumber[] phoneNumber;
}
