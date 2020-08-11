package ru.otus.svdovin.homework25;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class Homework25Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Homework25Application.class, args);
	}
}
