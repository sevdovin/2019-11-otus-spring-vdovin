package ru.otus.svdovin.homework02.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework02.config.QuestionSettings;

@Service
public class ConsoleMessageService implements MessageService {

    private final MessageSource ms;
    private final QuestionSettings settings;
    private final IOService ioService;

    public ConsoleMessageService(MessageSource ms, QuestionSettings settings, IOService ioService) {
        this.ms = ms;
        this.settings = settings;
        this.ioService = ioService;
    }

    @Override
    public void printMessage(String message) {
        ioService.outString(message);
    }

    @Override
    public void printMessageByKey(String messageKey) {
        ioService.outString(ms.getMessage(messageKey, null, settings.getQuestionLocale()));
    }

    @Override
    public void printMessageByKey(String messageKey, Object... args) {
        ioService.outString(ms.getMessage(messageKey, args, settings.getQuestionLocale()));
    }

    @Override
    public String readLn(String promptMessageKey) {
        return ioService.inString(ms.getMessage(promptMessageKey, null, settings.getQuestionLocale()));
    }

    @Override
    public String readLn() {
        return ioService.inString();
    }

    @Override
    public String getMessageByKey(String messageKey) {
        return ms.getMessage(messageKey, null, settings.getQuestionLocale());
    }

}