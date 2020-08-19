package ru.otus.svdovin.homework33;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
public class Homework33Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Homework33Application.class, args);
	}
}
