package ru.otus.svdovin.homework05.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.svdovin.homework05.config.ApplicationSettings;
import ru.otus.svdovin.homework05.domain.Question;
import ru.otus.svdovin.homework05.exception.QuestionsLoadingFailedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Методы сервиса загрузки вопросов должны ")
@ExtendWith(MockitoExtension.class)
public class QuestionLoaderImplTest {

    @Mock
    private ApplicationSettings applicationSettings;

    private QuestionLoaderImpl questionLoaderImpl;

    @BeforeEach
    void setUp() {
        questionLoaderImpl = new QuestionLoaderImpl(applicationSettings);
    }

    @Test
    public void loadQuestionsTest() throws QuestionsLoadingFailedException {
        given(applicationSettings.getQuestionFileName()).willReturn("/questions_ru_RU.csv");
        List<Question> questions = questionLoaderImpl.loadQuestions();
        verify(applicationSettings, times(1)).getQuestionFileName();
        assertAll("Чтение вопросов",
                () -> assertEquals(2, questions.size()),
                () -> assertEquals("Сколько будет 1 + 2? Выберите правильный ответ A-3, B-4, C-5", questions.get(0).getTextQuestion()),
                () -> assertEquals("A", questions.get(0).getCorrectAnswer()),
                () -> assertEquals("Сколько будет 2 + 3? Выберите правильный ответ A-4, B-5, C-6", questions.get(1).getTextQuestion()),
                () -> assertEquals("B", questions.get(1).getCorrectAnswer())
        );
    }
}
