package ru.otus.svdovin.homework04.service;

import ru.otus.svdovin.homework04.domain.Question;
import ru.otus.svdovin.homework04.exception.QuestionsLoadingFailedException;

import java.util.List;

public interface QuestionLoader {

    List<Question> loadQuestions() throws QuestionsLoadingFailedException;
}
