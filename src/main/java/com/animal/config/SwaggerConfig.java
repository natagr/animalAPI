package com.animal.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Nataliia",
                        email = "grabets.nata@gmail.com"
                ),
                description = "Animal API test task",
                title = "Animal API",
                version = "1.0"

        ),
        servers = {
                @Server(
                        description = "local env",
                        url = "http://localhost:8080"
                )
        }
)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("spring")
                .packagesToScan("com.animal")
                .build();
    }
}