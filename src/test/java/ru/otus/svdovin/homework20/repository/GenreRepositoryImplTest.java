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
import ru.otus.svdovin.homework20.domain.Genre;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Mongo для работы с жанрами должен ")
@DataMongoTest
@ComponentScan({"ru.otus.svdovin.homework20.testdata"})
class GenreRepositoryImplTest {

    private static final int GENRES_COUNT_INT = 4;

    private Genre genre1 = Genre.builder().genreId("idGenre1").genreName("Genre 1").build();
    private Genre genre2 = Genre.builder().genreId("idGenre2").genreName("Genre 2").build();
    private Genre genre3 = Genre.builder().genreId("idGenre3").genreName("Genre 3").build();
    private Genre genre4 = Genre.builder().genreId("idGenre4").genreName("Genre 4").build();

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private Mongock mongock;

    @BeforeEach
    public void init() {
        mongock.execute();
    }

    @DisplayName("возвращать список всех жанров")
    @Test
    void shouldFindGenreAll() {
        Flux<Genre> genres = genreRepository.findAll();
        StepVerifier.create(genres)
                .recordWith(ArrayList::new)
                .expectNextCount(GENRES_COUNT_INT)
                .consumeRecordedWith(results -> {
                    assertThat(results).hasSize(GENRES_COUNT_INT);
                    assertThat(results).contains(genre1, genre2, genre3, genre4);
                }).expectComplete().verify();
    }
}