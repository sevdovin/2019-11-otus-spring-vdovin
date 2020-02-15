package ru.otus.svdovin.homework04.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@ConfigurationProperties("application")
public class ApplicationSettings {

    private int successRate;
    private Locale locale;
    private String questionFileNameBase;

    public int getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(int successRate) {
        this.successRate = successRate;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setQuestionFileNameBase(String questionFileNameBase) {
        this.questionFileNameBase = questionFileNameBase;
    }

    public String getQuestionFileName() {
        return String.format("/%s_%s_%s.csv", questionFileNameBase, locale.getLanguage(), locale.getCountry());
    }
}