package ru.otus.svdovin.homework02.service;

import ru.otus.svdovin.homework02.exception.QuestionsLoadingFailedException;

public interface StartService {

    void startExam() throws QuestionsLoadingFailedException;
}
