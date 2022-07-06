package com.perficient.pbcpuserservice.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.perficient.pbcpuserservice.domain.EmailAddress;
import com.perficient.pbcpuserservice.model.UserDto;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Coercion of JSON to POJO and vice versa configuration.
 * @author tyler.barton
 * @version 1.0, 7/6/2022
 * @project PBCP-UserService
 */
@Configuration
public class ObjectMapperConfig {

    /**
     * Forces the mapper to try to convert empty strings.
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.postConfigurer(objectMapper -> {
                objectMapper.coercionConfigFor(UserDto.class)
                        .setCoercion(CoercionInputShape.EmptyString, CoercionAction.TryConvert);
            objectMapper.coercionConfigFor(EmailAddress.class)
                    .setCoercion(CoercionInputShape.EmptyString, CoercionAction.TryConvert);
        });
    }

    /**
     * Configures the ObjectMapper to ignore unknown properties and set them as null.
     * @return
     */
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        objectMapper.coercionConfigFor(LogicalType.POJO)
                .setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsNull);
        return objectMapper;
    }
}
