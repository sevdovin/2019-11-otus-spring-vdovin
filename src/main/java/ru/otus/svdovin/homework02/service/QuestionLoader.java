package ru.otus.svdovin.homework02.service;

import ru.otus.svdovin.homework02.domain.Question;
import ru.otus.svdovin.homework02.exception.QuestionsLoadingFailedException;

import java.util.List;

public interface QuestionLoader {

    List<Question> loadQuestions() throws QuestionsLoadingFailedException;
}
