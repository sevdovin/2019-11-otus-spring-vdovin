package ru.otus.svdovin.homework07.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.svdovin.homework07.dao.GenreDao;
import ru.otus.svdovin.homework07.domain.Genre;

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

    private static final long NEW_GENRE_ID = 7;
    private static final String NEW_GENRE_NAME = "Песня";
    private static final long DEFAULT_GENRE_ID = 4;

    @MockBean
    private GenreDao genreDao;

    @MockBean
    private MessageService messageService;

    @Autowired
    private GenreProvider genreProvider;

    @Test
    @DisplayName("должен корректно добавлять жанр")
    void shouldCeateGenre() {
        Genre genre = new Genre(NEW_GENRE_ID, NEW_GENRE_NAME);
        Mockito.when(genreDao.insert(genre)).thenReturn(NEW_GENRE_ID);
        long newId = genreProvider.createGenre(genre);
        assertThat(newId).isEqualTo(NEW_GENRE_ID);
    }

    @Test
    @DisplayName("возвращать ожидаемый жанр по его id")
    void shouldReturnExpectedGenreById() {
        Genre genre = new Genre(NEW_GENRE_ID, NEW_GENRE_NAME);
        Mockito.when(genreDao.getById(NEW_GENRE_ID)).thenReturn(Optional.of(genre));
        Genre actual = genreProvider.getGenreById(NEW_GENRE_ID).orElse(null);
        assertThat(actual).isEqualToComparingFieldByField(genre);
    }

    @Test
    @DisplayName("возвращать ожидаемого автора по его имени")
    void shouldReturnExpectedGenreByName() {
        Genre genre = new Genre(NEW_GENRE_ID, NEW_GENRE_NAME);
        List<Genre> list = new ArrayList<>();
        list.add(genre);
        Mockito.when(genreDao.getByName(NEW_GENRE_NAME)).thenReturn(list);
        List<Genre> listGenres = genreProvider.getGenreByName(NEW_GENRE_NAME);
        assertAll("Автор",
                () -> assertThat(listGenres.size()).isEqualTo(1),
                () -> assertThat(listGenres.get(0)).isEqualToComparingFieldByField(genre)
        );
    }

    @Test
    @DisplayName("возвращать всех авторов")
    void shouldReturnAllGenres() {
        Genre genre = new Genre(NEW_GENRE_ID, NEW_GENRE_NAME);
        List<Genre> list = new ArrayList<>();
        list.add(genre);
        Mockito.when(genreDao.getAll()).thenReturn(list);
        List<Genre> listGenres = genreProvider.getGenreAll();
        assertAll("Автор",
                () -> assertThat(listGenres.size()).isEqualTo(1),
                () -> assertThat(listGenres.get(0)).isEqualToComparingFieldByField(genre)
        );
    }

    @Test
    @DisplayName("изменять ожидаемого автора")
    void shouldUpdateExpectedGenre() {
        Genre genre = new Genre(NEW_GENRE_ID, NEW_GENRE_NAME);
        genreProvider.updateGenre(genre);
        verify(genreDao, times(1)).update(genre);
    }

    @Test
    @DisplayName("удалять ожидаемого автора")
    void shouldDeleteExpectedGenreById() {
        genreProvider.deleteGenreById(DEFAULT_GENRE_ID);
        verify(genreDao, times(1)).deleteById(DEFAULT_GENRE_ID);
    }
}