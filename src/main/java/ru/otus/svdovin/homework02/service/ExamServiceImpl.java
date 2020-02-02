package ru.otus.svdovin.homework02.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework02.config.QuestionSettings;
import ru.otus.svdovin.homework02.domain.ExamResult;
import ru.otus.svdovin.homework02.domain.Question;
import ru.otus.svdovin.homework02.exception.QuestionsLoadingFailedException;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    private final QuestionLoader questionLoader;
    private final IOService ioService;
    private final QuestionSettings questionSettings;

    public ExamServiceImpl(QuestionLoader questionLoader, IOService ioService, QuestionSettings questionSettings) {
        this.questionLoader = questionLoader;
        this.ioService = ioService;
        this.questionSettings = questionSettings;
    }

    public ExamResult examine() throws QuestionsLoadingFailedException {
        List<Question> questions = questionLoader.loadQuestions();
        int correctAnswerCount = 0;

        for(Question question : questions) {
            ioService.outString(question.getTextQuestion());
            String answer = ioService.inString();
            if (answer.equalsIgnoreCase(question.getCorrectAnswer())) {
                correctAnswerCount++;
            }
        }

        return new ExamResult(correctAnswerCount, questionSettings.getSuccessRate());
    }
}
