package ru.otus.svdovin.homework25.service.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.svdovin.homework25.domain.mongo.Genre;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class GenreStepService {

    private final MongoTemplate mongoTemplate;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Value("${application.chunkSize}")
    Integer chunkSize;

    @Bean(name="genreStep")
    public Step genreStep() {
        return stepBuilderFactory.get("genreStep")
                .<Genre, Genre>chunk(chunkSize)
                .reader(genreMongoItemReader(mongoTemplate))
                .processor(genreItemProcessor())
                .writer(genreJdbcBatchItemWriter())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Genre> genreMongoItemReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Genre>()
                .name("genreMongoItemReader")
                .template(mongoTemplate)
                .targetType(Genre.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Genre, Genre> genreItemProcessor() {
        return genre -> genre;
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Genre> genreJdbcBatchItemWriter() {
        JdbcBatchItemWriter<Genre> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO HW_GENRE (GENREID, GENRENAME) VALUES (:genreId, :genreName)");
        writer.setDataSource(dataSource);
        return writer;
    }
}
