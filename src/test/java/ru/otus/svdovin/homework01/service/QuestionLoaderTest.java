package ru.otus.svdovin.homework01.service;

import org.junit.Test;
import ru.otus.svdovin.homework01.domain.Question;
import ru.otus.svdovin.homework01.exception.QuestionsLoadingFailedException;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuestionLoaderTest {

    @Test
    public void loadQuestionsTest() throws QuestionsLoadingFailedException {
        QuestionLoader questionLoader = new QuestionLoaderImpl("/questions.csv");
        List<Question> questions = questionLoader.loadQuestions();

        assertEquals(2, questions.size());
        assertEquals("Сколько будет 1 + 2? Выберите правильный ответ A-3, B-4, C-5", questions.get(0).getTextQuestion());
        assertEquals("A", questions.get(0).getCorrectAnswer());
        assertEquals("Сколько будет 2 + 3? Выберите правильный ответ A-4, B-5, C-6", questions.get(1).getTextQuestion());
        assertEquals("B", questions.get(1).getCorrectAnswer());
    }
}
