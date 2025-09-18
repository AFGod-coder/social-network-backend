package com.example.socialdata.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(new Info()
                .title("Social Data Service API")
                .version("1.0")
                .description("Microservicio encargado de gestionar usuarios, posts y likes"));
    }
}
