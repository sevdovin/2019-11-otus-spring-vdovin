package ru.otus.svdovin.homework11.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework11.config.ApplicationSettings;

@Service
public class ConsoleMessageService implements MessageService {

    private final MessageSource ms;
    private final ApplicationSettings applicationSettings;
    private final IOService ioService;

    public ConsoleMessageService(MessageSource ms, ApplicationSettings applicationSettings, IOService ioService) {
        this.ms = ms;
        this.applicationSettings = applicationSettings;
        this.ioService = ioService;
    }

    @Override
    public void printMessage(String message) {
        ioService.outString(message);
    }

    @Override
    public void printMessageByKey(String messageKey) {
        ioService.outString(ms.getMessage(messageKey, null, applicationSettings.getLocale()));
    }

    @Override
    public void printMessageByKey(String messageKey, Object... args) {
        ioService.outString(ms.getMessage(messageKey, args, applicationSettings.getLocale()));
    }

    @Override
    public String readLn(String promptMessageKey) {
        return ioService.inString(ms.getMessage(promptMessageKey, null, applicationSettings.getLocale()));
    }

    @Override
    public String readLn() {
        return ioService.inString();
    }

    @Override
    public String getMessageByKey(String messageKey) {
        return ms.getMessage(messageKey, null, applicationSettings.getLocale());
    }
}
