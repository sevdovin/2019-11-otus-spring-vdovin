package ru.otus.svdovin.homework02.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Дао для работы с вопросами ")
public class QuestionTest {

    @Test
    @DisplayName("должен возвращать корректные текст вопроса и код(ы) правильного ответа")
    public void constructorTest() {
        Question question = new Question("Question", "Answer");
        assertAll("Вопрос",
                () -> assertEquals("Question", question.getTextQuestion()),
                () -> assertEquals("Answer", question.getCorrectAnswer())
        );
    }
}
