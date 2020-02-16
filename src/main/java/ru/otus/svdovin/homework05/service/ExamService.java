package ru.otus.svdovin.homework05.service;

import ru.otus.svdovin.homework05.domain.ExamResult;
import ru.otus.svdovin.homework05.exception.QuestionsLoadingFailedException;

public interface ExamService {

    ExamResult examine() throws QuestionsLoadingFailedException;
}
