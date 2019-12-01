package ru.otus.svdovin.homework01.domain;

public class ExamResult {

    private final int correctAnswerCount;
    private final int successRate;

    public ExamResult(int correctAnswerCount, int successRate) {
        this.correctAnswerCount = correctAnswerCount;
        this.successRate = successRate;
    }

    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public int getSuccessRate() {
        return successRate;
    }

    public boolean isPassed() {
        return correctAnswerCount >= successRate;
    }
}
