package com.carlosalbertoosf.personal_finance_api.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Personal Finance API")
                    .version("1.0")
                    .description("REST API for personal financial management, " +
                                "developed with Java and Spring Boot. " +
                                "The API provides resources for managing users, " +
                                "categories, and financial transactions, " +
                                "with data persistence using PostgreSQL.")
            );
    }
}
