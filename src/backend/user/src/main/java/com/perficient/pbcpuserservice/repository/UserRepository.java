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
    List<User> findAllById(Long userId);
    @Query("{'id': ?0, 'isDeleted': false}")
    List<User> findAllByIdAndDeleted(Long userId, boolean isDeleted);
    @Query("{'isDeleted': false}")
    List<User> findAllAndDeletedIsFalse();

    @Query("{'firstName': ?0, 'isDeleted': false}")
    List<User> findUserByFirstNameIsAndDeletedIsFalse(String firstName);
    @Query("{'lastName': ?0, 'isDeleted': false}")
    List<User> findUserByLastNameIsAndDeletedIsFalse(String lastName);
    @Query("{'firstName':?0, 'lastName': ?1, 'isDeleted': false}")
    List<User> findUserByFirstNameIsAndLastNameIsAndDeletedIsFalse(String firstName, String lastName);
}
