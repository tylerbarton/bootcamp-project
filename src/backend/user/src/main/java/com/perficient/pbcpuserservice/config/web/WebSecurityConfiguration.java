package com.perficient.pbcpuserservice.config.web;

import com.perficient.pbcpuserservice.config.web.CORSFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Attempts to implement OAuth2 authentication with Json Web Token using Spring Security.
 *
 * @author tyler.barton
 * @version 1.0, 7/12/2022
 * @project PBCP-UserService
 * @implNote https://www.techgeeknext.com/spring-boot-security/basic_authentication_web_security
 * @implNote https://github.com/Baeldung/spring-security-oauth/tree/master/oauth-rest
 */
@Configuration
@EnableWebSecurity(debug = true)
//@Profile(value = {"development", "production"})
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    // Gets the values from the application.properties file.
    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;


    /**
     * Accomplishes the following:
     * 1. Creates a user with the username "admin" and password "admin".
     * 2. Enables HTTP Basic and Form based authentication.
     * 3. Requires the user to have been authenticated within our application.
     * @param auth The authentication manager.
     * @throws Exception If there is an error.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // {noop} is required in Spring 5.0.x and uses the NoOpPasswordEncoder.
        auth.inMemoryAuthentication()
                .withUser(username).password("{noop}" + password).roles("USER").and()
                .withUser("admin").password("{noop}admin").roles("ADMIN").authorities("ROLE_ADMIN");
    }

    /**
     * Enables HTTP Basic and Form based authentication.
     * @param http
     * @throws Exception
     * @implNote https://spring.io/blog/2013/08/21/spring-security-3-2-0-rc1-highlights-csrf-protection/
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Add the CORS filter to the chain of filters.
        http.addFilterAfter(new CORSFilter(), BasicAuthenticationFilter.class);

        // Disable CSRF protection since the request is coming from the browser.
        http.csrf().disable();
    }
}
