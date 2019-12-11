package ru.otus.svdovin.homework01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.svdovin.homework01.exception.FileQuestionsNotExistsException;
import ru.otus.svdovin.homework01.service.StartService;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        StartService startService = context.getBean(StartService.class);
        try {
            startService.startExam();
        } catch (FileQuestionsNotExistsException e) {
            e.printStackTrace();
        }
    }
}
