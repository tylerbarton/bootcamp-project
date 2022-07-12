package com.perficient.pbcpuserservice.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * Interfaces with authorization server to get user information.
 *
 * @author tyler.barton
 * @version 1.0, 7/12/2022
 * @project PBCP-UserService
 */
@RestController
public class AdminLoginController {
    @GetMapping("/user/info")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal Jwt principal) {
        return Collections.singletonMap("user_name", principal.getClaimAsString("preferred_username"));
    }
}
