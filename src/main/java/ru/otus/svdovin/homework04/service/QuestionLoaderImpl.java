package ru.otus.svdovin.homework04.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework04.config.ApplicationSettings;
import ru.otus.svdovin.homework04.domain.Question;
import ru.otus.svdovin.homework04.exception.QuestionsLoadingFailedException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class QuestionLoaderImpl implements QuestionLoader {

    private final ApplicationSettings applicationSettings;

    public QuestionLoaderImpl(ApplicationSettings applicationSettings) {
        this.applicationSettings = applicationSettings;
    }

    @Override
    public List<Question> loadQuestions() throws QuestionsLoadingFailedException {
        InputStream inputStream = QuestionLoaderImpl.class.getResourceAsStream(applicationSettings.getQuestionFileName());
        if (inputStream == null) {
            throw new QuestionsLoadingFailedException("File with questions is absent!");
        }
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        List<Question> questions = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String[] items = scanner.nextLine().split(";");
            if (items.length == 2) {
                questions.add(new Question(items[0], items[1]));
            }
        }
        return questions;
    }

}
