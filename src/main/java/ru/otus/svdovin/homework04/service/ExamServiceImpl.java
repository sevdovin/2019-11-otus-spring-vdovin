package ru.otus.svdovin.homework04.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework04.config.ApplicationSettings;
import ru.otus.svdovin.homework04.domain.ExamResult;
import ru.otus.svdovin.homework04.domain.Question;
import ru.otus.svdovin.homework04.exception.QuestionsLoadingFailedException;

import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {

    private final QuestionLoader questionLoader;
    private final IOService ioService;
    private final ApplicationSettings applicationSettings;

    public ExamServiceImpl(QuestionLoader questionLoader, IOService ioService, ApplicationSettings applicationSettings) {
        this.questionLoader = questionLoader;
        this.ioService = ioService;
        this.applicationSettings = applicationSettings;
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

        return new ExamResult(correctAnswerCount, applicationSettings.getSuccessRate());
    }
}
