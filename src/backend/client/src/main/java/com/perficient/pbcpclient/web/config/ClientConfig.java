package com.perficient.pbcpclient.web.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * @author tyler.barton
 * @version 1.0, 6/28/2022
 * @project PBCP-Client
 */
@Getter
@Configuration // This is a Spring Configuration class
@PropertySource("classpath:application.properties") // Link to the application.properties file
public class ClientConfig implements EnvironmentAware {
    @Value("${service.host}")
    private String host;
    @Value("${service.api.user.path}")
    private String apiUserPath;

    @Autowired
    private Environment env;

//    public ClientConfig() {
//        System.out.println("ClientConfig.ClientConfig()");
//        env.getProperty("service.host");
//    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Circumvents Spring's autowiring that is occuring too late.
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }
}
