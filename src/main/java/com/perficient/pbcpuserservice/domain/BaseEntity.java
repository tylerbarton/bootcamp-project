package com.perficient.pbcpuserservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 *
 * @author tyler.barton
 * @version 1.0, 6/24/2022
 * @project PBCP-UserService
 */
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isDeleted; // soft delete
}
