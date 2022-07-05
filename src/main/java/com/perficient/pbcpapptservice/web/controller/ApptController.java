package com.perficient.pbcpapptservice.web.controller;

import com.perficient.pbcpapptservice.model.AppointmentDto;
import com.perficient.pbcpapptservice.services.ApptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tyler.barton
 * @version 1.0, 6/29/2022
 * @project PBCP-ApptService
 */
@RestController
@RequestMapping("${service.api.path}")
public class ApptController {
    @Autowired
    private ApptService apptService;

    /**
     * Get a list of all appointments
     * @return a list of appointments and 200 (OK) status if successful else 404 (Not Found)
     */
    @GetMapping("/all")
    public ResponseEntity getAppts() {
        List<AppointmentDto> apptDtos = apptService.getAppts();
        if(apptDtos.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(apptDtos, HttpStatus.OK);
    }

    /**
     * Get an appointment by id
     * @param id the appointment id
     * @return an appointment and 200 (OK) status if successful else 404 (Not Found)
     */
    @GetMapping("/appts/{id}")
    public ResponseEntity getAppt(@PathVariable("id") Long id) {
        AppointmentDto apptDto = apptService.getAppt(id);
        if(apptDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(apptDto, HttpStatus.OK);
    }

    /**
     * Create an appointment
     * @param appointmentDto the appointment to create
     * @return a 201 (CREATED) status if successful else 400 (BAD REQUEST)
     */
    @PostMapping(path="/appts", consumes={"application/json"})
    public ResponseEntity createAppt(@Validated @RequestBody AppointmentDto appointmentDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;");
        AppointmentDto result = apptService.createAppt(appointmentDto);
        if(result == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, headers, HttpStatus.CREATED);
    }

    /**
     * Update an appointment by id
     * @param id the appointment id
     * @param appointmentDto the appointment data
     * @return a 204 (NO CONTENT) status if successful else 404 (Not Found)
     */
    @PutMapping("/appts/{id}")
    public ResponseEntity updateAppt(@PathVariable("id")Long id, @RequestBody @Validated AppointmentDto appointmentDto) {
        if(!apptService.exists(id)) {
            return createAppt(appointmentDto);
        }
        apptService.updateAppt(id, appointmentDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete an appointment by id with the option of a hard or soft (default) delete.
     * @param id the appointment id
     * @return a 204 (NO CONTENT) status if successful else 404 (Not Found)
     */
    @DeleteMapping("/appts/{id}")
    public ResponseEntity deleteAppt(@PathVariable("id")Long id, @RequestParam(value = "hardDelete", defaultValue = "false") boolean hardDelete) {
        if(!apptService.exists(id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        if(hardDelete) {
            apptService.hardDeleteAppt(id);
        } else {
            apptService.softDeleteAppt(id);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Cancel an existing appointment by id
     * @param id the appointment id
     * @return a 204 (NO CONTENT) status if successful else 404 (Not Found)
     */
    @PutMapping("/appts/{id}/cancel")
    public ResponseEntity cancelAppt(@PathVariable("id")Long id) {
        if(!apptService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        apptService.cancelAppt(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Change the start time of an existing appointment by id
     * @param id the appointment id
     * @return a 204 (NO CONTENT) status if successful else 404 (Not Found)
     */
    @PutMapping("/appts/{id}/reschedule")
    public ResponseEntity rescheduleAppt(@PathVariable("id") Long id) {
        if(!apptService.exists(id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        apptService.rescheduleAppt(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Complete an existing appointment by id
     * @param id the appointment id
     * @return a 204 (NO CONTENT) status if successful else 404 (Not Found)
     */
    @PutMapping("/appts/{id}/complete")
    public ResponseEntity completeAppt(@PathVariable("id") Long id) {
        if(!apptService.exists(id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        apptService.completeAppt(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
