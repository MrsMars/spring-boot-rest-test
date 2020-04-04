package com.aoher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile("dev")
public class DevelopmentOnlyConfiguration {

    @Bean
    public String dummy() {
        return "something";
    }
}
