package ru.otus.svdovin.homework05.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.svdovin.homework05.config.ApplicationSettings;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Методы сервиса сообщений консоли должны ")
@SpringBootTest
public class ConsoleMessageServiceBootTest {

    @MockBean
    private MessageSource ms;

    @MockBean
    private ApplicationSettings applicationSettings;

    @MockBean
    private IOService ioService;

    @Autowired
    private ConsoleMessageService consoleMessageService;

    private Object a = new Object(), b = new Object();

    @Test
    @DisplayName("печатать заданную строку")
    public void printMessageTest() {
        consoleMessageService.printMessage("test");
        verify(ioService, times(1)).outString("test");
    }

    @Test
    @DisplayName("печатать локализованное сообщение")
    public void printMessageByKeyTest() {
        given(applicationSettings.getLocale()).willReturn(Locale.US);
        given(ms.getMessage("test", null, Locale.US)).willReturn("test_en");
        consoleMessageService.printMessageByKey("test");
        verify(applicationSettings, times(1)).getLocale();
        verify(ms, times(1)).getMessage("test", null, Locale.US);
        verify(ioService, times(1)).outString("test_en");
    }

    @Test
    @DisplayName("печатать локализованное сообщение с аргументами")
    public void printMessageByKeyWithArgsTest() {
        given(applicationSettings.getLocale()).willReturn(Locale.US);
        Object[] args = {a, b};
        given(ms.getMessage("test",args, Locale.US)).willReturn("test_args_en");
        consoleMessageService.printMessageByKey("test", a, b);
        verify(applicationSettings, times(1)).getLocale();
        verify(ms, times(1)).getMessage("test", args, Locale.US);
        verify(ioService, times(1)).outString("test_args_en");
    }

    @Test
    @DisplayName("вводить строку")
    public void readLnTest() {
        given(ioService.inString()).willReturn("test");
        String result = consoleMessageService.readLn();
        verify(ioService, times(1)).inString();
        assertEquals("test", result);
    }

    @Test
    @DisplayName("печатать локализованное сообщение с вводом строки")
    public void readLnWithPrintMessageByKeyTest() {
        given(applicationSettings.getLocale()).willReturn(Locale.US);
        given(ms.getMessage("test", null, Locale.US)).willReturn("test_en");
        given(ioService.inString("test_en")).willReturn("test_result");
        String result = consoleMessageService.readLn("test");
        verify(applicationSettings, times(1)).getLocale();
        verify(ms, times(1)).getMessage("test", null, Locale.US);
        verify(ioService, times(1)).inString("test_en");
        assertEquals("test_result", result);
    }

    @Test
    @DisplayName("возвращать локализованное сообщение")
    public void getMessageByKeyTest() {
        given(applicationSettings.getLocale()).willReturn(Locale.US);
        given(ms.getMessage("test", null, Locale.US)).willReturn("test_en");
        String result = consoleMessageService.getMessageByKey("test");
        verify(applicationSettings, times(1)).getLocale();
        verify(ms, times(1)).getMessage("test", null, Locale.US);
        assertEquals("test_en", result);
    }
}
