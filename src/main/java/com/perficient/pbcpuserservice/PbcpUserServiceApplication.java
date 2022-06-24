package com.perficient.pbcpuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.perficient.pbcpuserservice.repository")
public class PbcpUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PbcpUserServiceApplication.class, args);
    }

}
