package ru.otus.svdovin.homework25.service.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class ClearDbStepService {

    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private final static String sqlDeleteComment = "DELETE FROM HW_COMMENT";
    private final static String sqlDeleteBook = "DELETE FROM HW_BOOK";
    private final static String sqlDeleteAuthor = "DELETE FROM HW_AUTHOR";
    private final static String sqlDeleteGenre = "DELETE FROM HW_GENRE";

    @Bean(name="cleanDbStep")
    public Step cleanDbStep() {
        return stepBuilderFactory.get("cleanDbStep")
                .tasklet(deleteDataTasklet())
                .build();
    }

    @StepScope
    @Bean
    public Tasklet deleteDataTasklet() {
        return (contribution, chunkContext) -> {
            new JdbcTemplate(dataSource).execute(sqlDeleteComment);
            new JdbcTemplate(dataSource).execute(sqlDeleteBook);
            new JdbcTemplate(dataSource).execute(sqlDeleteAuthor);
            new JdbcTemplate(dataSource).execute(sqlDeleteGenre);
            return RepeatStatus.FINISHED;
        };
    }

}
