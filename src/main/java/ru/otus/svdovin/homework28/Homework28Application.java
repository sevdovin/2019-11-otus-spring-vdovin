package ru.otus.svdovin.homework28;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@IntegrationComponentScan
@EnableIntegration
public class Homework28Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Homework28Application.class, args);
	}
}
