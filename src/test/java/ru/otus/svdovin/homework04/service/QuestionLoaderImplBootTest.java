package ru.otus.svdovin.homework04.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.svdovin.homework04.domain.Question;
import ru.otus.svdovin.homework04.exception.QuestionsLoadingFailedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Методы сервиса загрузки вопросов должны ")
@SpringBootTest
public class QuestionLoaderImplBootTest {

    @Autowired
    private QuestionLoaderImpl questionLoaderImpl;

    @Test
    public void loadQuestionsTest() throws QuestionsLoadingFailedException {
        List<Question> questions = questionLoaderImpl.loadQuestions();
        assertAll("Чтение вопросов",
                () -> assertEquals(2, questions.size()),
                () -> assertEquals("Сколько будет 1 + 2? Выберите правильный ответ A-3, B-4, C-5", questions.get(0).getTextQuestion()),
                () -> assertEquals("A", questions.get(0).getCorrectAnswer()),
                () -> assertEquals("Сколько будет 2 + 3? Выберите правильный ответ A-4, B-5, C-6", questions.get(1).getTextQuestion()),
                () -> assertEquals("B", questions.get(1).getCorrectAnswer())
        );
    }
}
