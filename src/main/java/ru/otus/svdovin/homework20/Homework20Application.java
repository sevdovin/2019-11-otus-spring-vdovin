package ru.otus.svdovin.homework20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class Homework20Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Homework20Application.class, args);
	}
}
