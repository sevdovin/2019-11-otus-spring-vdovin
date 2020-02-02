package ru.otus.svdovin.homework02.service;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConsoleService implements IOService {

    private final PrintStream printStream;
    private final Scanner scanner;

    public ConsoleService(InputStream inputStream, OutputStream outputStream) {
        this.printStream = new PrintStream(outputStream);
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public void outString(String text) {
        printStream.println(text);
    }

    @Override
    public String inString() {
        return scanner.nextLine();
    }

    @Override
    public String inString(String text) {
        outString(text);
        return scanner.nextLine();
    }
}
