package com.perficient.pbcpuserservice.web.controller.exceptions;

/**
 * Resource not found exception
 *
 * @author tyler.barton
 * @version 1.0, 6/22/2022
 * @project perf-bootcamp-project
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
