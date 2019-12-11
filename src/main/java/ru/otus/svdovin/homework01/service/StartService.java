package ru.otus.svdovin.homework01.service;

import ru.otus.svdovin.homework01.exception.FileQuestionsNotExistsException;

public interface StartService {

    void startExam() throws FileQuestionsNotExistsException;
}
