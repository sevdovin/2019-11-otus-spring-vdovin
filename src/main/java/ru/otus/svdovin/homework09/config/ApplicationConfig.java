package ru.otus.svdovin.homework09.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.svdovin.homework09.domain.ConsoleContext;

@Configuration
public class ApplicationConfig {

    @Bean
    public ConsoleContext consoleContext() {
        return new ConsoleContext(java.lang.System.in, java.lang.System.out);
    }

}
