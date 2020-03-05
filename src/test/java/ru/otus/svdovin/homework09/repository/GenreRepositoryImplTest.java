package ru.otus.svdovin.homework09.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.svdovin.homework09.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами должен ")
@DataJpaTest
@Import(GenreRepositoryImpl.class)
class GenreRepositoryImplTest {

    private static final String GENRE_NEW_NAME = "New Genre";
    private static final long GENRES_COUNT_LONG = 4;
    private static final int GENRES_COUNT_INT = 4;
    private static final long GENRE_ID_EXIST = 4;
    private static final long GENRE_ID_NOT_EXIST = 5;
    private static final String GENRE_NAME_EXISTS = "Genre 4";
    private static final String GENRE_NAME_NOT_EXISTS = "Genre 5";

    @Autowired
    private GenreRepositoryImpl genreRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        val genre = new Genre(0, GENRE_NEW_NAME);
        genreRepository.insert(genre);
        assertThat(genre.getGenreId()).isGreaterThan(0);
        val actualGenre = em.find(Genre.class, genre.getGenreId());
        assertThat(actualGenre).isNotNull().isEqualToComparingFieldByField(genre);
    }

    @DisplayName("изменять наименование жанра")
    @Test
    void shouldUpdateGenreName() {
        Genre genre1 = em.find(Genre.class, GENRE_ID_EXIST);
        String oldName = genre1.getGenreName();
        genre1.setGenreName(GENRE_NEW_NAME);
        Genre genre2 = genreRepository.update(genre1);
        assertAll("Жанр изменился",
                () -> assertThat(genre2).isNotNull(),
                () -> assertThat(genre2).isEqualToComparingFieldByField(genre1),
                () -> assertThat(genre2.getGenreName()).isNotEqualTo(oldName)
        );
    }

    @DisplayName("удалять жанр")
    @Test
    void shouldDeleteGenre() {
        Genre genre1 = em.find(Genre.class, GENRE_ID_EXIST);
        genreRepository.deleteById(GENRE_ID_EXIST);
        assertAll("Жанр удален",
                () -> assertThat(genre1).isNotNull(),
                () -> assertThat(em.find(Genre.class, GENRE_ID_EXIST)).isNull()
        );
    }

    @DisplayName("возвращать жанр по его id")
    @Test
    void shouldFindGenreById() {
        Optional<Genre> genre = genreRepository.getById(GENRE_ID_EXIST);
        assertThat(genre).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("genreName", GENRE_NAME_EXISTS);
    }

    @DisplayName("возвращать список жанров по имени")
    @Test
    void shouldFindGenreByName() {
        assertAll("Список жанров по имени",
                () -> assertThat(genreRepository.getByName(GENRE_NAME_EXISTS)).isNotNull().hasSize(1),
                () -> assertThat(genreRepository.getByName(GENRE_NAME_NOT_EXISTS)).isNotNull().hasSize(0)
        );
    }

    @DisplayName("возвращать список всех жанров")
    @Test
    void shouldFindGenreAll() {
        List<Genre> genres = genreRepository.getAll();
        assertThat(genres).isNotNull().hasSize(GENRES_COUNT_INT);
    }

    @DisplayName("возвращать количество жанров")
    @Test
    void shouldReturnGenreCount() {
        long genresCount = genreRepository.count();
        assertThat(genresCount).isEqualTo(GENRES_COUNT_LONG);
    }

    @DisplayName("проверять наличие жанр по идентификатору")
    @Test
    void shouldCheckExistById() {
        assertAll("Существование id жанра",
                () -> assertThat(genreRepository.existsById(GENRE_ID_EXIST)).isEqualTo(true),
                () -> assertThat(genreRepository.existsById(GENRE_ID_NOT_EXIST)).isEqualTo(false)
        );
    }

    @DisplayName("проверять наличие жанр по имени")
    @Test
    void shouldCheckExistByName() {
        assertAll("Существование имени жанра",
                () -> assertThat(genreRepository.existsByName(GENRE_NAME_EXISTS)).isEqualTo(true),
                () -> assertThat(genreRepository.existsByName(GENRE_NAME_NOT_EXISTS)).isEqualTo(false)
        );
    }



}