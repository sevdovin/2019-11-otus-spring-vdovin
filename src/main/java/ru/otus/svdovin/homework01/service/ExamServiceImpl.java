package ru.otus.svdovin.homework01.service;

import ru.otus.svdovin.homework01.domain.ExamResult;
import ru.otus.svdovin.homework01.domain.Question;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ExamServiceImpl implements ExamService {

    private final QuestionLoader questionLoader;
    private final int successRate;

    public ExamServiceImpl(QuestionLoader questionLoader, int successRate) {
        this.questionLoader = questionLoader;
        this.successRate = successRate;
    }

    public ExamResult examine(Scanner scanner) {
        List<Question> questions = null;
        try {
            questions = questionLoader.loadQuestions();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int correctAnswerCount = 0;

        for(Question question : questions) {
            System.out.println(question.getTextQuestion());
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase(question.getCorrectAnswer())) {
                correctAnswerCount++;
            }
        }

        return new ExamResult(correctAnswerCount, successRate);
    }
}
