package ru.otus.svdovin.homework01.service;

import ru.otus.svdovin.homework01.domain.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionLoader {

    List<Question> loadQuestions() throws IOException;
}
