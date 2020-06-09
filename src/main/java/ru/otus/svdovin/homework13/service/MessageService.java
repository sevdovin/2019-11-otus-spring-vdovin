package ru.otus.svdovin.homework13.service;

public interface MessageService {
    void printMessage(String message);
    void printMessageByKey(String messageKey);
    void printMessageByKey(String messageKey, Object... args);
    String readLn(String promptMessageKey);
    String readLn();
    String getMessageByKey(String messageKey);
}
