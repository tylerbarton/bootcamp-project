package com.perficient.pbcpclient.web.client;

import com.perficient.pbcpclient.model.UserDto;
import com.perficient.pbcpclient.web.config.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Spring Web Client
 *
 * @author tyler.barton
 * @version 1.0, 6/28/2022
 * @project PBCP-Client
 */
@Component
public class Client {

    @Autowired
    private ClientConfig config;

    private final RestTemplate restTemplate;

    /**
     * Initialize a new client object
     * @param restTemplateBuilder
     */
    public Client(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    /**
     * Gets a user by id from the service
     * @param userId The id of the user
     * @return UserDto The user object
     */
    public UserDto getUserById(long userId) {
        return restTemplate.getForObject(config.getApiUserPath() + "/" + userId, UserDto.class);
    }

    /**
     * Saves a new user to the service
     * @param userDto The user to save
     * @return URI: Resource locator
     * @throws Exception if the user is null
     */
    public UserDto saveNewUser(UserDto userDto) throws Exception {
        if (userDto == null) {
            throw new Exception("User is null");
        }
        return restTemplate.postForObject(config.getApiUserPath(), userDto, UserDto.class);
    }

    /**
     * Updates a user in the service
     * @param userDto The user to update
     * @throws Exception if the user is null
     */
    public void updateUser(UserDto userDto) throws Exception {
        if (userDto == null) {
            throw new Exception("User is null");
        }
        restTemplate.put(config.getApiUserPath() + "/" + userDto.getUserId(), userDto);
    }

    /**
     * Deletes a user from the service
     * @param userId The id of the user to delete
     * @param hardDelete If true, the user will be deleted from the service. If false, the user will be marked as deleted.
     */
    public void deleteUser(long userId, boolean hardDelete) {
        restTemplate.delete(config.getApiUserPath() + "/" + userId + "?hardDelete=" + hardDelete);
    }
}
