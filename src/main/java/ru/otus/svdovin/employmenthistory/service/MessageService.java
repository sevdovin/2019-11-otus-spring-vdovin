package ru.otus.svdovin.employmenthistory.service;

public interface MessageService {
    String getMessage(String key);
    String getFormatedMessage(String key, Object... args);
}
