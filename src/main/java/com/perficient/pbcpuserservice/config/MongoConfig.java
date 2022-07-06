package com.perficient.pbcpuserservice.config;

import com.perficient.pbcpuserservice.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.UUID;

/**
 * @author tyler.barton
 * @version 1.0, 6/27/2022
 * @project PBCP-UserService
 */

@Configuration
@EnableMongoRepositories("com.perficient.pbcpuserservice.repository")
public class MongoConfig {

    /**
     * Guarantees synchronous execution.
     * @implNote Uses the {@link BeforeConvertCallback} to guarantee that the {@link User}'s ID is set to a UUID.
     * @implNote UUID is generated using a work-around to keep the value as a long for usability.
     * @return The BeforeConvertCallback for the User entity.
     */
    @Bean
    public BeforeConvertCallback<User> beforeSaveCallback() {
        return (entity, collection) -> {
            if (entity.getId() == null) {
                Long uuid = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
                entity.setId(uuid);
                entity.setDeleted(false);
            }
            return entity;
        };
    }
}
