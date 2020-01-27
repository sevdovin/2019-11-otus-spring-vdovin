package ru.otus.svdovin.homework01.service;

import ru.otus.svdovin.homework01.domain.ExamResult;
import ru.otus.svdovin.homework01.exception.QuestionsLoadingFailedException;

public class StartServiceImpl implements StartService {

    private static final String LASTNAME = "Введите Вашу фамилию: ";
    private static final String FIRSTNAME = "Введите Ваше имя: ";
    private static final String SUCCESS = "%s, Вы прошли тест! Количество правильных ответов : %d.";
    private static final String FAIL = "%s, Вы НЕ прошли тест! Количество правильных ответов : %d.";

    String lastName = null;
    String firstName = null;

    private final ExamService examService;
    private  final IOService ioService;

    public StartServiceImpl(ExamService examService, IOService ioService) {
        this.examService = examService;
        this.ioService = ioService;
    }

    @Override
    public void startExam() throws QuestionsLoadingFailedException {
        getFIO();
        ExamResult examResult = examService.examine(ioService);
        ioService.outString(String.format(examResult.isPassed() ? SUCCESS : FAIL, lastName + " " + firstName, examResult.getCorrectAnswerCount()));
    }

    private void getFIO() {
        ioService.outString(LASTNAME);
        lastName = ioService.inString();
        ioService.outString(FIRSTNAME);
        firstName = ioService.inString();
    }
}
