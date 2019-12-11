package ru.otus.svdovin.homework01.service;

import ru.otus.svdovin.homework01.domain.ExamResult;
import ru.otus.svdovin.homework01.exception.FileQuestionsNotExistsException;

public interface ExamService {

    ExamResult examine(ConsoleService consoleService) throws FileQuestionsNotExistsException;
}
