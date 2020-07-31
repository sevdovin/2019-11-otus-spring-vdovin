package ru.otus.svdovin.homework20.repository;

import com.github.cloudyrock.mongock.Mongock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.svdovin.homework20.domain.Author;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Mongo для работы с авторами должен ")
@DataMongoTest
@ComponentScan({"ru.otus.svdovin.homework20.testdata"})
class AuthorRepositoryImplTest {

    private static final int AUTHORS_COUNT_INT = 4;

    private Author author1 = Author.builder().authorId("idAuthor1").authorName("Author 1").build();
    private Author author2 = Author.builder().authorId("idAuthor2").authorName("Author 2").build();
    private Author author3 = Author.builder().authorId("idAuthor3").authorName("Author 3").build();
    private Author author4 = Author.builder().authorId("idAuthor4").authorName("Author 4").build();

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private Mongock mongock;

    @BeforeEach
    public void init() {
        mongock.execute();
    }

    @DisplayName("возвращать список всех авторов")
    @Test
    void shouldFindAuthorAll() {
        Flux<Author> authors = authorRepository.findAll();
        StepVerifier.create(authors)
                .recordWith(ArrayList::new)
                .expectNextCount(AUTHORS_COUNT_INT)
                .consumeRecordedWith(results -> {
                    assertThat(results).hasSize(AUTHORS_COUNT_INT);
                    assertThat(results).contains(author1, author2, author3, author4);
                }).expectComplete().verify();
    }
}