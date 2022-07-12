package com.perficient.pbcpuserservice.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Attempts to implement OAuth2 authentication with Json Web Token using Spring Security.
 *
 * @author tyler.barton
 * @version 1.0, 7/12/2022
 * @project PBCP-UserService
 * @implNote https://www.techgeeknext.com/spring-boot-security/basic_authentication_web_security
 * @implNote https://github.com/Baeldung/spring-security-oauth/tree/master/oauth-rest
 */
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {// @formatter:off
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic();
//                .oauth2Login();// @formatter:on


//        http.cors()
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/user/info", "/api/foos/**")
//                .hasAuthority("SCOPE_read")
//                .antMatchers(HttpMethod.POST, "/api/foos")
//                .hasAuthority("SCOPE_write")
//                .anyRequest()
//                .authenticated()
//                .and()
//                .oauth2ResourceServer()
//                .jwt();
    }//@formatter:on
}
