package ru.otus.svdovin.homework05.service;

import ru.otus.svdovin.homework05.domain.Question;
import ru.otus.svdovin.homework05.exception.QuestionsLoadingFailedException;

import java.util.List;

public interface QuestionLoader {

    List<Question> loadQuestions() throws QuestionsLoadingFailedException;
}
