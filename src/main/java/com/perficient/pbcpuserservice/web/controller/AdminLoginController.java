package com.perficient.pbcpuserservice.web.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * Interfaces with authorization server to get user information.
 *
 * @author tyler.barton
 * @version 1.0, 7/12/2022
 * @project PBCP-UserService
 */
@RestController
@CrossOrigin(origins = "*") // Enables cross-origin requests, allowing the service to be called from a different domain.
public class AdminLoginController {
//    @GetMapping("/user/info")
//    public Map<String, Object> getUserInfo(@AuthenticationPrincipal Jwt principal) {
//        return Collections.singletonMap("user_name", principal.getClaimAsString("preferred_username"));
//    }
}
