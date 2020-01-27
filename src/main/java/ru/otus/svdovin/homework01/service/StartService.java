package ru.otus.svdovin.homework01.service;

import ru.otus.svdovin.homework01.exception.QuestionsLoadingFailedException;

public interface StartService {

    void startExam() throws QuestionsLoadingFailedException;
}
