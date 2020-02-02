package ru.otus.svdovin.homework02.service;

import ru.otus.svdovin.homework02.domain.ExamResult;
import ru.otus.svdovin.homework02.exception.QuestionsLoadingFailedException;

public interface ExamService {

    ExamResult examine() throws QuestionsLoadingFailedException;
}
