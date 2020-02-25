package ru.otus.svdovin.homework07.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.svdovin.homework07.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final long NEW_GENRE_ID = 7;
    private static final String NEW_GENRE_NAME = "Песня";
    private static final long DEFAULT_GENRE_ID = 4;
    private static final String DEFAULT_GENRE_NAME = "Genre 4";
    private static final String DEFAULT_GENRE_NEW_NAME = "Genre 4";
    private static final int EXPECTED_GENRE_COUNT = 4;

    @Autowired
    private GenreDaoJdbc jdbc;

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        Genre genre = new Genre(NEW_GENRE_ID, NEW_GENRE_NAME);
        long newId = jdbc.insert(genre);
        genre.setGenreId(newId);
        Genre actual = jdbc.getById(newId).orElse(null);
        assertThat(actual).isEqualToComparingFieldByField(genre);
    }

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre actual = jdbc.getById(DEFAULT_GENRE_ID).orElse(null);
        assertThat(actual.getGenreName()).isEqualTo(DEFAULT_GENRE_NAME);
    }

    @DisplayName("возвращать ожидаемое количество жанров в БД")
    @Test
    void shouldReturnExpectedGenreCount() {
        assertThat(jdbc.count()).isEqualTo(EXPECTED_GENRE_COUNT);
    }

    @DisplayName("удалять ожидаемый жанр по его id")
    @Test
    void shouldDeleteExpectedGenreById() {
        jdbc.deleteById(DEFAULT_GENRE_ID);
        Genre actual = jdbc.getById(DEFAULT_GENRE_ID).orElse(null);
        assertThat(actual).isEqualTo(null);
    }

    @DisplayName("возвращать ожидаемый жанр по его имени")
    @Test
    void shouldReturnExpectedGenreByName() {
        List<Genre> listGenres = jdbc.getByName(DEFAULT_GENRE_NAME);
        assertAll("Жанр",
                () -> assertThat(listGenres.size()).isEqualTo(1),
                () -> assertThat(listGenres.get(0).getGenreName()).isEqualTo(DEFAULT_GENRE_NAME)
        );
    }

    @DisplayName("возвращать все жанры")
    @Test
    void shouldReturnAllGenres() {
        List<Genre> listGenres = jdbc.getAll();
        assertThat(listGenres.size()).isEqualTo(4);
    }

    @DisplayName("изменять ожидаемый жанр")
    @Test
    void shouldUpdateExpectedGenre() {
        Genre genre = new Genre(DEFAULT_GENRE_ID, DEFAULT_GENRE_NEW_NAME);
        jdbc.update(genre);
        Genre actual = jdbc.getById(DEFAULT_GENRE_ID).orElse(null);
        assertThat(actual).isEqualToComparingFieldByField(genre);
    }
}