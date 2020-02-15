package ru.otus.svdovin.homework04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.svdovin.homework04.service.StartService;

@SpringBootApplication
public class Homework04Application {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = SpringApplication.run(Homework04Application.class);
		StartService startService = context.getBean(StartService.class);
		startService.startExam();
	}

}
