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
import ru.otus.svdovin.homework25.domain.mongo.Author;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class AuthorStepService {

    private final MongoTemplate mongoTemplate;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Value("${application.chunkSize}")
    Integer chunkSize;

    @Bean(name="authorStep")
    public Step authorStep() {
        return stepBuilderFactory.get("authorStep")
                .<Author, Author>chunk(chunkSize)
                .reader(authorItemReader(mongoTemplate))
                .processor(authorItemProcessor())
                .writer(authorJdbcBatchItemWriter())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Author> authorItemReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Author>()
                .name("authorItemReader")
                .template(mongoTemplate)
                .targetType(Author.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Author, Author> authorItemProcessor() {
        return author -> author;
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Author> authorJdbcBatchItemWriter() {
        JdbcBatchItemWriter<Author> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO HW_AUTHOR (AUTHORID, AUTHORNAME) VALUES (:authorId, :authorName)");
        writer.setDataSource(dataSource);
        return writer;
    }
}
