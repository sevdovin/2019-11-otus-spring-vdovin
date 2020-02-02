package ru.otus.svdovin.homework02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.svdovin.homework02.service.StartService;

@ComponentScan
public class Main {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        StartService startService = context.getBean(StartService.class);
        startService.startExam();
    }
}
