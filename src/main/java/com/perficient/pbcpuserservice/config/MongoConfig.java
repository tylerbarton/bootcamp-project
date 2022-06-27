package com.perficient.pbcpuserservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author tyler.barton
 * @version 1.0, 6/27/2022
 * @project PBCP-UserService
 */

@Configuration
@EnableMongoRepositories("com.perficient.pbcpuserservice.repository")
public class MongoConfig {
}
