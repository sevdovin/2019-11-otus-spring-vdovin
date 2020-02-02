package ru.otus.svdovin.homework02.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.io.InputStream;
import java.io.OutputStream;

@Configuration
@PropertySource("classpath:questions.properties")
public class QuestionConfig {

    @Bean
    public InputStream inputStream() {
        return java.lang.System.in;
    }

    @Bean
    public OutputStream outputStream() {
        return java.lang.System.out;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/i18n/bundle");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}