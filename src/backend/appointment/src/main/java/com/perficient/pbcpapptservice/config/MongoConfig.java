package com.perficient.pbcpapptservice.config;

import com.perficient.pbcpapptservice.domain.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;
import java.util.UUID;

/**
 * @author tyler.barton
 * @version 1.0, 7/8/2022
 * @project PBCP-ApptService
 */

@Configuration
@EnableMongoRepositories("com.perficient.pbcpapptservice.repository")
public class MongoConfig {
    /**
     * Guarantees synchronous execution.
     * @implNote Uses the {@link BeforeConvertCallback} to guarantee that the {@link User}'s ID is set to a UUID.
     * @implNote UUID is generated using a work-around to keep the value as a long for usability.
     * @return The BeforeConvertCallback for the User entity.
     */
    @Bean
    public BeforeConvertCallback<Appointment> beforeSaveCallback() {
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
