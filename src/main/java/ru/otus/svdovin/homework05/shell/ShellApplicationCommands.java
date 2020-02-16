package ru.otus.svdovin.homework05.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.svdovin.homework05.service.StartService;

@ShellComponent
public class ShellApplicationCommands {

    private String userName;
    private final StartService startService;

    public ShellApplicationCommands(StartService startService) {
        this.startService = startService;
    }

    @ShellMethod(value = "Login command", key = {"login", "l"})
    public String login(@ShellOption(defaultValue = "vdserg") String userName) {
        this.userName = userName;
        return String.format("Добро пожаловать: %s", userName);
    }

    @ShellMethod(value = "Run testing!", key = {"run", "r"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public void startTesting() throws Exception {
        startService.startExam();
    }

    private Availability isPublishEventCommandAvailable() {
        return userName == null? Availability.unavailable("Сначала залогиньтесь"): Availability.available();
    }

}
