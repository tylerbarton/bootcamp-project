package com.perficient.pbcpapptservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

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
