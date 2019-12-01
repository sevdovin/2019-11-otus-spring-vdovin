package ru.otus.svdovin.homework01.service;

import ru.otus.svdovin.homework01.domain.ExamResult;

import java.util.Scanner;

public class StartServiceImpl implements StartService {

    private static final String LASTNAME = "Введите Вашу фамилию: ";
    private static final String FIRSTNAME = "Введите Ваше имя: ";
    private static final String SUCCESS = "%s, Вы прошли тест! Количество правильных ответов : %d.";
    private static final String FAIL = "%s, Вы НЕ прошли тест! Количество правильных ответов : %d.";

    private final ExamService examService;

    public StartServiceImpl(ExamService examService) {
        this.examService = examService;
    }

    @Override
    public void startExam() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(LASTNAME);
        String lastName = scanner.nextLine();
        System.out.print(FIRSTNAME);
        String firstName = scanner.nextLine();

        ExamResult examResult = examService.examine(scanner);
        System.out.println(String.format(examResult.isPassed() ? SUCCESS : FAIL, lastName + " " + firstName, examResult.getCorrectAnswerCount()));
    }
}
