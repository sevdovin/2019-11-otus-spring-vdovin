package ru.otus.svdovin.homework01.service;

import ru.otus.svdovin.homework01.domain.ExamResult;
import ru.otus.svdovin.homework01.domain.Question;
import ru.otus.svdovin.homework01.exception.FileQuestionsNotExistsException;

import java.util.List;

public class ExamServiceImpl implements ExamService {

    private final QuestionLoader questionLoader;
    private final int successRate;

    public ExamServiceImpl(QuestionLoader questionLoader, int successRate) {
        this.questionLoader = questionLoader;
        this.successRate = successRate;
    }

    public ExamResult examine(ConsoleService consoleService) throws FileQuestionsNotExistsException {
        List<Question> questions = questionLoader.loadQuestions();
        int correctAnswerCount = 0;

        for(Question question : questions) {
            consoleService.outString(question.getTextQuestion());
            String answer = consoleService.inString();
            if (answer.equalsIgnoreCase(question.getCorrectAnswer())) {
                correctAnswerCount++;
            }
        }

        return new ExamResult(correctAnswerCount, successRate);
    }
}
