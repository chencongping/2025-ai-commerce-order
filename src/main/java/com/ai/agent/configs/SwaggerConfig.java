package com.ai.agent.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value(("${spring.application.name}"))
    private String applicationName;
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(applicationName + " API Document")
                        .version("1.0.0")
                        .description(applicationName)
                        .contact(new Contact()
                                .name("ChenCongPing")
                                .email("https://github.com/chencongping/" + applicationName)));
    }
}
