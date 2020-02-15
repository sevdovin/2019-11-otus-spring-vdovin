package ru.otus.svdovin.homework04.service;

import ru.otus.svdovin.homework04.domain.ExamResult;
import ru.otus.svdovin.homework04.exception.QuestionsLoadingFailedException;

public interface ExamService {

    ExamResult examine() throws QuestionsLoadingFailedException;
}
