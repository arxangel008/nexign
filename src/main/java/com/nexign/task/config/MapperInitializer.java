package com.nexign.task.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.module.jsr310.Jsr310Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

/**
 * Создает и настраивает маппер
 */
@Configuration
@RequiredArgsConstructor
public class MapperInitializer {

    /**
     * Создает маппер. Проходит по всем конфигурациям, передает им текущий инстанс маппера для конфигурирования
     */
    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();

        modelMapper
                .getConfiguration()
                .setMatchingStrategy(STRICT);
        modelMapper.registerModule(new Jsr310Module());
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        return modelMapper;
    }
}
