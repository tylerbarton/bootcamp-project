package com.perficient.pbcpapptservice.repository;

import com.perficient.pbcpapptservice.domain.Appointment;
import com.perficient.pbcpapptservice.domain.ApptStatus;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author tyler.barton
 * @version 1.0, 6/29/2022
 * @project PBCP-ApptService
 */
@Repository
public interface ApptRepository extends MongoRepository<Appointment, Long> {
    Optional<Appointment> findAppointmentByNameAndDeletedIsFalse(String name);
    boolean existsAppointmentByIdAndAndDeletedIsFalse(Long id);

    @Query("{'isDeleted': false}")
    List<Appointment> findAppointmentsByDeletedIsFalse();
    List<Appointment> findAppointmentsByDeletedIsFalseAndStatus(ApptStatus status);
    List<Appointment> findAppointmentsByDeletedTrue();

    @Query("{'id': ?0, 'isDeleted': false}")
    boolean existsByIdAndDeletedIsFalse(Long id);
}

