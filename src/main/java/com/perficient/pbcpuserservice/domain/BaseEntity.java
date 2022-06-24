package com.perficient.pbcpuserservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

/**
 *
 *
 * @author tyler.barton
 * @version 1.0, 6/24/2022
 * @project PBCP-UserService
 */
@Getter
@Setter
public class BaseEntity {

    @Id
    private Long id;
    private boolean isDeleted; // soft delete
}
