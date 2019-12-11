package ru.otus.svdovin.homework01.service;

import ru.otus.svdovin.homework01.domain.Question;
import ru.otus.svdovin.homework01.exception.FileQuestionsNotExistsException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionLoaderImpl implements QuestionLoader {

    private final String questionsFileName;

    public QuestionLoaderImpl(String questionsFileName) {
        this.questionsFileName = questionsFileName;
    }

    @Override
    public List<Question> loadQuestions() throws FileQuestionsNotExistsException {
        InputStream inputStream = QuestionLoaderImpl.class.getResourceAsStream(questionsFileName);
        if (inputStream == null) {
            throw new FileQuestionsNotExistsException("File with questions is absent!");
        }
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        List<Question> questions = new ArrayList<Question>();
        while (scanner.hasNextLine()) {
            String[] items = scanner.nextLine().split(";");
            if (items.length == 2) {
                questions.add(new Question(items[0], items[1]));
            }
        }
        return questions;
    }

}
