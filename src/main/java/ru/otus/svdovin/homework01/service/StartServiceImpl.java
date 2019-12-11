package ru.otus.svdovin.homework01.service;

import ru.otus.svdovin.homework01.domain.ExamResult;
import ru.otus.svdovin.homework01.exception.FileQuestionsNotExistsException;

public class StartServiceImpl implements StartService {

    private static final String LASTNAME = "Введите Вашу фамилию: ";
    private static final String FIRSTNAME = "Введите Ваше имя: ";
    private static final String SUCCESS = "%s, Вы прошли тест! Количество правильных ответов : %d.";
    private static final String FAIL = "%s, Вы НЕ прошли тест! Количество правильных ответов : %d.";

    String lastName = null;
    String firstName = null;

    private final ExamService examService;

    public StartServiceImpl(ExamService examService) {
        this.examService = examService;
    }

    @Override
    public void startExam() throws FileQuestionsNotExistsException {
        ConsoleService consoleService = new ConsoleService(System.in, System.out);
        getFIO(consoleService);
        ExamResult examResult = examService.examine(consoleService);
        consoleService.outString(String.format(examResult.isPassed() ? SUCCESS : FAIL, lastName + " " + firstName, examResult.getCorrectAnswerCount()));
    }

    private void getFIO(ConsoleService consoleService) {
        consoleService.outString(LASTNAME);
        lastName = consoleService.inString();
        consoleService.outString(FIRSTNAME);
        firstName = consoleService.inString();
    }
}
