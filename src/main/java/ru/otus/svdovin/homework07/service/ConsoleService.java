package ru.otus.svdovin.homework07.service;

import org.springframework.stereotype.Service;
import ru.otus.svdovin.homework07.domain.ConsoleContext;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConsoleService implements IOService {

    private final PrintStream printStream;
    private final Scanner scanner;

    public ConsoleService(ConsoleContext consoleContext) {
        this.printStream = new PrintStream(consoleContext.getOutputStream());
        this.scanner = new Scanner(consoleContext.getInputStream());
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
