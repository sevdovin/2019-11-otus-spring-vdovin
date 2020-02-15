package ru.otus.svdovin.homework04.service;

import ru.otus.svdovin.homework04.exception.QuestionsLoadingFailedException;

public interface StartService {

    void startExam() throws QuestionsLoadingFailedException;
}
