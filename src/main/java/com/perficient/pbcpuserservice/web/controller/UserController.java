package com.perficient.pbcpuserservice.web.controller;

import com.perficient.pbcpuserservice.model.UserDto;
import com.perficient.pbcpuserservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tyler.barton
 * @version 1.0, 6/23/2022
 * @project PBCP-UserService
 */
@RestController
@RequestMapping("${service.api.path}")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get a user by id
     *
     * @param userId the user id
     * @return the user and 200 (OK) if found, else 404 (NOT FOUND)
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") Long userId){
        UserDto userDto = userService.getUserById(userId);
        if (userDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * List all users in the service
     * @return a list of users and 200 (OK) status if successful else 404 (Not Found)
     */
    @GetMapping()
    public ResponseEntity<List<UserDto>> listUsers(){
        List<UserDto> userDtos = userService.listUsers();
        if(userDtos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    /**
     * Delete a user by id with the option of a hard or soft delete (soft delete is default).
     * @param userId the user id
     * @return a 204 (NO CONTENT) status if successful else 404 (Not Found)
     * @apiNote example call: delete http://localhost:8080/api/v1/user/1?hardDelete=true
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") Long userId, @RequestParam(value = "hardDelete", defaultValue = "false") boolean hardDelete){
        if (userService.getUserById(userId) == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(userId, hardDelete);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Create a new user
     * @param userDto the user information
     * @return a 201 (CREATED) status if successful else 400 (Bad Request)
     */
    @PostMapping(consumes={"application/json"})
    public ResponseEntity createUser(@Validated @RequestBody UserDto userDto) {
        UserDto savedDto = userService.createNewUser(userDto);
        if (savedDto == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    /**
     * Update an existing user
     * @param userId the user id
     * @param userDto the new user information
     * @return a 200 (OK) status if successful else 404 (Not Found)
     */
    @PutMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable("userId") Long userId, @RequestBody @Validated UserDto userDto) throws Throwable {
        UserDto savedDto = userService.updateUser(userId, userDto);
        if (savedDto == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(savedDto, HttpStatus.OK);
    }
}
