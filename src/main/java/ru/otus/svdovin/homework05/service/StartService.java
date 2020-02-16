package ru.otus.svdovin.homework05.service;

import ru.otus.svdovin.homework05.exception.QuestionsLoadingFailedException;

public interface StartService {

    void startExam() throws QuestionsLoadingFailedException;
}
