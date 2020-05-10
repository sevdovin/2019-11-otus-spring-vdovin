package ru.otus.svdovin.employmenthistory.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.svdovin.employmenthistory.config.ApplicationSettings;

@Service
public class ConsoleMessageService implements MessageService {

    private final MessageSource ms;
    private final ApplicationSettings applicationSettings;

    public ConsoleMessageService(MessageSource ms, ApplicationSettings applicationSettings) {
        this.ms = ms;
        this.applicationSettings = applicationSettings;
    }

    @Override
    public String getMessage(String key) {
        return ms.getMessage(key, null, applicationSettings.getLocale());
    }

    @Override
    public String getFormatedMessage(String key, Object... args) {
        return ms.getMessage(key, args, applicationSettings.getLocale());
    }
}
