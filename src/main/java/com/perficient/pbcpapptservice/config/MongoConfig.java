package com.perficient.pbcpapptservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author tyler.barton
 * @version 1.0, 7/8/2022
 * @project PBCP-ApptService
 */

@Configuration
@EnableMongoRepositories("com.perficient.pbcpapptservice.repository")
public class MongoConfig {
}
