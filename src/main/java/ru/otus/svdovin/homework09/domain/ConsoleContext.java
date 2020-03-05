package ru.otus.svdovin.homework09.domain;

import java.io.InputStream;
import java.io.OutputStream;

public class ConsoleContext {

    private InputStream inputStream;
    private OutputStream outputStream;

    public ConsoleContext(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }
}
