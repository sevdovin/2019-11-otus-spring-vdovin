package ru.otus.svdovin.homework25.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class ShellCommander {

    private final Job migrateMongoToPostgreSql;
    private final JobLauncher jobLauncher;

    @SneakyThrows
    @ShellMethod(key = {"m", "sm"}, value = "Migrate mongo data to PostgreSQL")
    public void migrateMongoToPostgreSql() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(migrateMongoToPostgreSql, jobParameters);
    }
}
