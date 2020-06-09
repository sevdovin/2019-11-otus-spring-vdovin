package ru.otus.svdovin.homework13.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.svdovin.homework13.domain.ConsoleContext;

@Configuration
public class ApplicationConfig {

    private static final String CHANGELOGS_PACKAGE = "ru.otus.svdovin.homework13.changelogs";

    @Bean
    public Mongock mongock(MongoProps mongoProps, MongoClient mongoClient) {
        return new SpringMongockBuilder(mongoClient, mongoProps.getDatabase(), CHANGELOGS_PACKAGE)
                .build();
    }

    @Bean
    public ConsoleContext consoleContext() {
        return new ConsoleContext(java.lang.System.in, java.lang.System.out);
    }
}
