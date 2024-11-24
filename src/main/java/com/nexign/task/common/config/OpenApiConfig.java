package com.nexign.task.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.String.format;

/**
 * Конфигурация OpenApi
 */
@Configuration
public class OpenApiConfig {

    private static final String TITLE = "NEXIGN";
    private static final String DESCRIPTION_TEMPLATE = "Обработка заданий";

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * Бин конфигурации OpenAPI
     *
     * @return конфигурация OpenAPI
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(TITLE)
                        .description(format(DESCRIPTION_TEMPLATE, applicationName)));
    }
}
