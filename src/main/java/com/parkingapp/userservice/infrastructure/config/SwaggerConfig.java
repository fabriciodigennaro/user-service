package com.parkingapp.userservice.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI openApi() {
        return new OpenAPI()
            .info(new Info()
                .title("User service")
                .version("1.0.0")
                .description(
                    "This API allows managing user information, including retrieving user details and creating new user records."
                )
            );
    }
}
