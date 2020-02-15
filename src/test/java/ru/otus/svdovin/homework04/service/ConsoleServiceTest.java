package ru.otus.svdovin.homework04.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.svdovin.homework04.domain.ConsoleContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Методы сервиса консоли должны ")
public class ConsoleServiceTest {

    private ConsoleService consoleService;

    private String testOutputString = "kolya";
    private String testInputString = "vasya";

    private OutputStream outputStream;
    private InputStream inputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        inputStream = new ByteArrayInputStream(testInputString.getBytes(StandardCharsets.UTF_8));
        ConsoleContext consoleContext = new ConsoleContext(inputStream, outputStream);
        consoleService = new ConsoleService(consoleContext);
    }

    @Test
    @DisplayName("выводить заданную строку с переводом строки")
    public void outStringTest() {
        consoleService.outString(testOutputString);
        assertEquals(testOutputString + "\r\n", outputStream.toString());
    }

    @Test
    @DisplayName("возвращать введенную строку")
    public void inStringTest() {
        String result = consoleService.inString();
        assertEquals(testInputString, result);
    }

    @Test
    @DisplayName("выводить заданную строку с переводом строки и возвращать введенную строку")
    public void inOutStringTest() {
        String result = consoleService.inString(testOutputString);
        assertAll("Вывод строки и ввод",
                () -> assertEquals(testOutputString + "\r\n", outputStream.toString()),
                () -> assertEquals(testInputString, result)
        );
    }
}
