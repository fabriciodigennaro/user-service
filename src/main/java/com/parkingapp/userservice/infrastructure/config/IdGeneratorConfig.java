package com.parkingapp.userservice.infrastructure.config;

import com.parkingapp.userservice.domain.user.common.IdGenerator;
import com.parkingapp.userservice.infrastructure.RandomIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGeneratorConfig {

    @Bean
    IdGenerator idGenerator() {
        return new RandomIdGenerator();
    }
}
