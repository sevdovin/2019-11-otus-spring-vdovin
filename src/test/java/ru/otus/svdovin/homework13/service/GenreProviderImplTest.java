package ru.otus.svdovin.homework13.service;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.svdovin.homework13.domain.Genre;
import ru.otus.svdovin.homework13.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис для работы с жанрами ")
@SpringBootTest(classes = {GenreProviderImpl.class})
class GenreProviderImplTest {

    private static final String NEW_GENRE_ID = "idGenre7";
    private static final String NEW_GENRE_NAME = "Песня";
    private static final String DEFAULT_GENRE_ID = "idGenre4";
    private static final String GENRE_ID_EXIST = "idGenre4";
    private static final String GENRE_ID_NOT_EXIST = "idGenre5";
    private static final String GENRE_NAME_EXISTS = "Genre 4";
    private static final String GENRE_NAME_NOT_EXISTS = "Genre 5";

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private GenreProvider genreProvider;

    @Test
    @DisplayName("должен корректно добавлять жанр")
    void shouldCeateGenre() {
        val genre = Genre.builder()
                .genreId(NEW_GENRE_ID)
                .genreName(NEW_GENRE_NAME)
                .build();
        Mockito.when(genreRepository.save(genre)).thenReturn(genre);
        String newId = genreProvider.createGenre(genre);
        assertThat(newId).isEqualTo(NEW_GENRE_ID);
    }

    @Test
    @DisplayName("возвращать ожидаемый жанр по его id")
    void shouldReturnExpectedGenreById() {
        val genre = Genre.builder()
                .genreId(NEW_GENRE_ID)
                .genreName(NEW_GENRE_NAME)
                .build();
        Mockito.when(genreRepository.findById(NEW_GENRE_ID)).thenReturn(Optional.of(genre));
        Genre actual = genreProvider.getGenreById(NEW_GENRE_ID).orElse(null);
        assertThat(actual).isEqualToComparingFieldByField(genre);
    }

    @Test
    @DisplayName("возвращать ожидаемый жанр по его имени")
    void shouldReturnExpectedGenreByName() {
        val genre = Genre.builder()
                .genreId(NEW_GENRE_ID)
                .genreName(NEW_GENRE_NAME)
                .build();
        List<Genre> list = new ArrayList<>();
        list.add(genre);
        Mockito.when(genreRepository.findByGenreName(NEW_GENRE_NAME)).thenReturn(list);
        List<Genre> listGenres = genreProvider.getGenreByName(NEW_GENRE_NAME);
        assertAll("Жанр",
                () -> assertThat(listGenres.size()).isEqualTo(1),
                () -> assertThat(listGenres.get(0)).isEqualToComparingFieldByField(genre)
        );
    }

    @Test
    @DisplayName("возвращать все жанры")
    void shouldReturnAllGenres() {
        val genre = Genre.builder()
                .genreId(NEW_GENRE_ID)
                .genreName(NEW_GENRE_NAME)
                .build();
        List<Genre> list = new ArrayList<>();
        list.add(genre);
        Mockito.when(genreRepository.findAll()).thenReturn(list);
        List<Genre> listGenres = genreProvider.getGenreAll();
        assertAll("Жанр",
                () -> assertThat(listGenres.size()).isEqualTo(1),
                () -> assertThat(listGenres.get(0)).isEqualToComparingFieldByField(genre)
        );
    }

    @Test
    @DisplayName("изменять ожидаемый жанр")
    void shouldUpdateExpectedGenre() {
        val genre = Genre.builder()
                .genreId(NEW_GENRE_ID)
                .genreName(NEW_GENRE_NAME)
                .build();
        genreProvider.updateGenre(genre);
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    @DisplayName("удалять ожидаемый жанр")
    void shouldDeleteExpectedGenreById() {
        genreProvider.deleteGenreById(DEFAULT_GENRE_ID);
        verify(genreRepository, times(1)).deleteById(DEFAULT_GENRE_ID);
    }

    @Test
    @DisplayName("проверять наличие жанра по идентификатору")
    void shouldCheckExistById() {
        Mockito.when(genreRepository.existsById(GENRE_ID_EXIST)).thenReturn(true);
        Mockito.when(genreRepository.existsById(GENRE_ID_NOT_EXIST)).thenReturn(false);
        assertAll("Существование id жанра",
                () -> assertThat(genreProvider.existsById(GENRE_ID_EXIST)).isEqualTo(true),
                () -> assertThat(genreProvider.existsById(GENRE_ID_NOT_EXIST)).isEqualTo(false)
        );
    }

    @Test
    @DisplayName("проверять наличие жанра по имени")
    void shouldCheckExistByName() {
        Mockito.when(genreRepository.existsByGenreName(GENRE_NAME_EXISTS)).thenReturn(true);
        Mockito.when(genreRepository.existsByGenreName(GENRE_NAME_NOT_EXISTS)).thenReturn(false);
        assertAll("Существование имени жанра",
                () -> assertThat(genreProvider.existsByName(GENRE_NAME_EXISTS)).isEqualTo(true),
                () -> assertThat(genreProvider.existsByName(GENRE_NAME_NOT_EXISTS)).isEqualTo(false)
        );
    }
}