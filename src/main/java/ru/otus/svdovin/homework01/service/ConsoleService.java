package ru.otus.svdovin.homework01.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleService {

    private final PrintStream printStream;
    private final Scanner scanner;

    public ConsoleService(InputStream inputStream, OutputStream outputStream) {
        this.printStream = new PrintStream(outputStream);
        this.scanner = new Scanner(inputStream);
    }

    public void outString(String text) {
        printStream.println(text);
    }

    public String inString() {
        return scanner.nextLine();
    }
}
