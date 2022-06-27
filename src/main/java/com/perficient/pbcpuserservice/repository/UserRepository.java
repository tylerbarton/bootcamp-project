package com.perficient.pbcpuserservice.repository;

import com.perficient.pbcpuserservice.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tyler.barton
 * @version 1.0, 6/23/2022
 * @project PBCP-UserService
 */
@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    @Query("{'isDeleted': true}")
    void softDeleteById(Long userId);

    List<User> findAllById(Long userId);
}
