package ru.otus.svdovin.homework01.service;

import ru.otus.svdovin.homework01.domain.ExamResult;
import ru.otus.svdovin.homework01.domain.Question;
import ru.otus.svdovin.homework01.exception.QuestionsLoadingFailedException;

import java.util.List;

public class ExamServiceImpl implements ExamService {

    private final QuestionLoader questionLoader;
    private final int successRate;

    public ExamServiceImpl(QuestionLoader questionLoader, int successRate) {
        this.questionLoader = questionLoader;
        this.successRate = successRate;
    }

    public ExamResult examine(IOService ioService) throws QuestionsLoadingFailedException {
        List<Question> questions = questionLoader.loadQuestions();
        int correctAnswerCount = 0;

        for(Question question : questions) {
            ioService.outString(question.getTextQuestion());
            String answer = ioService.inString();
            if (answer.equalsIgnoreCase(question.getCorrectAnswer())) {
                correctAnswerCount++;
            }
        }

        return new ExamResult(correctAnswerCount, successRate);
    }
}
