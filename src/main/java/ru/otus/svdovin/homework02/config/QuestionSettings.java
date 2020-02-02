package ru.otus.svdovin.homework02.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class QuestionSettings {
    private Locale locale;
    private String questionFileName;
    private int successRate;

    public QuestionSettings(@Value("${success.rate:2}") String successRate,
                            @Value("${locale.lang:ru}") String lang,
                            @Value("${locale.country:RU}") String country,
                            @Value("${question.file.name.base:questions}") String questionFileNameBase) {
        locale = new Locale(lang, country);
        questionFileName = String.format("/%s_%s_%s.csv", questionFileNameBase, lang, country);
    }

    public int getSuccessRate() {
        return successRate;
    }

    public Locale getQuestionLocale() {
        return locale;
    }

    public String getQuestionFileName() {
        return questionFileName;
    }
}