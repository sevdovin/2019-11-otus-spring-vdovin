package ru.otus.svdovin.homework04.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.svdovin.homework04.config.ApplicationSettings;
import ru.otus.svdovin.homework04.domain.ExamResult;
import ru.otus.svdovin.homework04.domain.Question;
import ru.otus.svdovin.homework04.exception.QuestionsLoadingFailedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Методы сервиса загрузки вопросов должны ")
@ExtendWith(MockitoExtension.class)
public class ExamServiceImplTest {

    @Mock
    private QuestionLoaderImpl questionLoaderImpl;

    @Mock
    private IOService ioService;

    @Mock
    private ApplicationSettings applicationSettings;

    private ExamServiceImpl examServiceImpl;

    @BeforeEach
    void setUp() {
        examServiceImpl = new ExamServiceImpl(questionLoaderImpl, ioService, applicationSettings);
    }

    @Test
    public void examineTest() throws QuestionsLoadingFailedException {
        given(applicationSettings.getSuccessRate()).willReturn(3);
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("question1", "A"));
        questions.add(new Question("question2", "B"));
        questions.add(new Question("question3", "C"));
        given(questionLoaderImpl.loadQuestions()).willReturn(questions);
        given(ioService.inString()).willReturn("A", "B", "C");

        ExamResult result = examServiceImpl.examine();
        verify(questionLoaderImpl, times(1)).loadQuestions();
        verify(applicationSettings, times(1)).getSuccessRate();
        verify(ioService, times(3)).outString(any());
        verify(ioService, times(3)).inString();
        assertEquals(true, result.isPassed());
    }
}
