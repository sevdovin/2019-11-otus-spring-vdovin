package ru.otus.svdovin.homework02.domain;

public class Question {

    private String textQuestion;
    private String correctAnswer;

    public Question(String textQuestion, String correctAnswer) {
        this.textQuestion = textQuestion;
        this.correctAnswer = correctAnswer;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String toString() {
        return textQuestion + ";" + correctAnswer;
    }
}
