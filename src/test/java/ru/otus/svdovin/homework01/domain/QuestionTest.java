package ru.otus.svdovin.homework01.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuestionTest {

    @Test
    public void constructorTest() {
        Question question = new Question("Question", "Answer");
        assertEquals("Question", question.getTextQuestion());
        assertEquals("Answer", question.getCorrectAnswer());
    }
}
