package ru.otus.svdovin.homework01.service;

import ru.otus.svdovin.homework01.domain.ExamResult;
import ru.otus.svdovin.homework01.exception.QuestionsLoadingFailedException;

public interface ExamService {

    ExamResult examine(IOService ioService) throws QuestionsLoadingFailedException;
}
