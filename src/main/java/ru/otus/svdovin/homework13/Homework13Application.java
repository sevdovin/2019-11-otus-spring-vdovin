package ru.otus.svdovin.homework13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class Homework13Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Homework13Application.class, args);
	}
}
