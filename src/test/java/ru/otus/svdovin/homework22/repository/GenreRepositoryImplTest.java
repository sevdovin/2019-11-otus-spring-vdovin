package ru.otus.svdovin.homework22.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.svdovin.homework22.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами должен ")
@DataJpaTest
class GenreRepositoryImplTest {

    private static final String GENRE_NEW_NAME = "New Genre";
    private static final long GENRES_COUNT_LONG = 4;
    private static final int GENRES_COUNT_INT = 4;
    private static final long GENRE_ID_EXIST = 4;
    private static final long GENRE_ID_NOT_EXIST = 5;
    private static final String GENRE_NAME_EXISTS = "Genre 4";
    private static final String GENRE_NAME_NOT_EXISTS = "Genre 5";

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        val genre = new Genre(0, GENRE_NEW_NAME);
        long newId = genreRepository.save(genre).getGenreId();
        val actual = em.find(Genre.class, newId);
        assertThat(actual).isNotNull().isEqualToComparingFieldByField(genre);
    }

    @DisplayName("изменять наименование жанра")
    @Test
    void shouldUpdateGenreName() {
        Genre genre1 = em.find(Genre.class, GENRE_ID_EXIST);
        String oldName = genre1.getGenreName();
        em.detach(genre1);
        genre1.setGenreName(GENRE_NEW_NAME);
        Genre genre2 = genreRepository.save(genre1);
        assertThat(genre2.getGenreName()).isNotEqualTo(oldName).isEqualTo(GENRE_NEW_NAME);
    }

    @DisplayName("удалять жанр")
    @Test
    void shouldDeleteGenre() {
        val genre1 = em.find(Genre.class, GENRE_ID_EXIST);
        assertThat(genre1).isNotNull();
        em.detach(genre1);
        genreRepository.deleteById(GENRE_ID_EXIST);
        val genre2 = em.find(Genre.class, GENRE_ID_EXIST);
        assertThat(genre2).isNull();
    }

    @DisplayName("возвращать жанр по его id")
    @Test
    void shouldFindGenreById() {
        Optional<Genre> genre = genreRepository.findById(GENRE_ID_EXIST);
        assertThat(genre).isNotEmpty().get()
                .hasFieldOrPropertyWithValue("genreName", GENRE_NAME_EXISTS);
    }

    @DisplayName("возвращать список жанров по имени")
    @Test
    void shouldFindGenreByName() {
        assertAll("Список жанров по имени",
                () -> assertThat(genreRepository.findByGenreName(GENRE_NAME_EXISTS)).isNotNull().hasSize(1),
                () -> assertThat(genreRepository.findByGenreName(GENRE_NAME_NOT_EXISTS)).isNotNull().hasSize(0)
        );
    }

    @DisplayName("возвращать список всех жанров")
    @Test
    void shouldFindGenreAll() {
        List<Genre> genres = genreRepository.findAll();
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
                () -> assertThat(genreRepository.existsByGenreName(GENRE_NAME_EXISTS)).isEqualTo(true),
                () -> assertThat(genreRepository.existsByGenreName(GENRE_NAME_NOT_EXISTS)).isEqualTo(false)
        );
    }
}