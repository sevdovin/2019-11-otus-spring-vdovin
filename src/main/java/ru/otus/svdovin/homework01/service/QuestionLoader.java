package ru.otus.svdovin.homework01.service;

import ru.otus.svdovin.homework01.domain.Question;
import ru.otus.svdovin.homework01.exception.QuestionsLoadingFailedException;

import java.util.List;

public interface QuestionLoader {

    List<Question> loadQuestions() throws QuestionsLoadingFailedException;
}
