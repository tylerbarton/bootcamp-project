package com.perficient.pbcpuserservice.domain;

import org.hibernate.sql.ordering.antlr.Node;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.persistence.*;
import java.util.Set;

/**
 * @author tyler.barton
 * @version 1.0, 6/23/2022
 * @project PBCP-UserService
 */
@Entity
@Table("users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @ElementCollection
    private EmailAddress[] emailAddress;
    private PhoneNumber[] phoneNumber;
}
