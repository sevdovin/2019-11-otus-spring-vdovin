package ru.otus.svdovin.homework11.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.svdovin.homework11.domain.Author;
import ru.otus.svdovin.homework11.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис для работы с авторами ")
@SpringBootTest(classes = {AuthorProviderImpl.class})
class AuthorProviderImplTest {

    private static final long NEW_AUTHOR_ID = 7;
    private static final String NEW_AUTHOR_NAME = "Вася";
    private static final long DEFAULT_AUTHOR_ID = 4;
    private static final long AUTHOR_ID_EXIST = 4;
    private static final long AUTHOR_ID_NOT_EXIST = 5;
    private static final String AUTHOR_NAME_EXISTS = "Author 4";
    private static final String AUTHOR_NAME_NOT_EXISTS = "Author 5";

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorProvider authorProvider;

    @Test
    @DisplayName("должен корректно добавлять автора")
    void shouldCeateAuthor() {
        Author author = new Author(NEW_AUTHOR_ID, NEW_AUTHOR_NAME);
        Mockito.when(authorRepository.save(author)).thenReturn(author);
        long newId = authorProvider.createAuthor(author);
        assertThat(newId).isEqualTo(NEW_AUTHOR_ID);
    }

    @Test
    @DisplayName("возвращать ожидаемого автора по его id")
    void shouldReturnExpectedAuthorById() {
        Author author = new Author(NEW_AUTHOR_ID, NEW_AUTHOR_NAME);
        Mockito.when(authorRepository.findById(NEW_AUTHOR_ID)).thenReturn(Optional.of(author));
        Author actual = authorProvider.getAuthorById(NEW_AUTHOR_ID).orElse(null);
        assertThat(actual).isEqualToComparingFieldByField(author);
    }

    @Test
    @DisplayName("возвращать ожидаемого автора по его имени")
    void shouldReturnExpectedAuthorByName() {
        Author author = new Author(NEW_AUTHOR_ID, NEW_AUTHOR_NAME);
        List<Author> list = new ArrayList<>();
        list.add(author);
        Mockito.when(authorRepository.findByAuthorName(NEW_AUTHOR_NAME)).thenReturn(list);
        List<Author> listAuthors = authorProvider.getAuthorByName(NEW_AUTHOR_NAME);
        assertAll("Автор",
                () -> assertThat(listAuthors.size()).isEqualTo(1),
                () -> assertThat(listAuthors.get(0)).isEqualToComparingFieldByField(author)
        );
    }

    @Test
    @DisplayName("возвращать всех авторов")
    void shouldReturnAllAuthors() {
        Author author = new Author(NEW_AUTHOR_ID, NEW_AUTHOR_NAME);
        List<Author> list = new ArrayList<>();
        list.add(author);
        Mockito.when(authorRepository.findAll()).thenReturn(list);
        List<Author> listAuthors = authorProvider.getAuthorAll();
        assertAll("Автор",
                () -> assertThat(listAuthors.size()).isEqualTo(1),
                () -> assertThat(listAuthors.get(0)).isEqualToComparingFieldByField(author)
        );
    }

    @Test
    @DisplayName("изменять ожидаемого автора")
    void shouldUpdateExpectedAuthor() {
        Author author = new Author(NEW_AUTHOR_ID, NEW_AUTHOR_NAME);
        authorProvider.updateAuthor(author);
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    @DisplayName("удалять ожидаемого автора")
    void shouldDeleteExpectedAuthorById() {
        authorProvider.deleteAuthorById(DEFAULT_AUTHOR_ID);
        verify(authorRepository, times(1)).deleteById(DEFAULT_AUTHOR_ID);
    }

    @Test
    @DisplayName("проверять наличие автора по идентификатору")
    void shouldCheckExistById() {
        Mockito.when(authorRepository.existsById(AUTHOR_ID_EXIST)).thenReturn(true);
        Mockito.when(authorRepository.existsById(AUTHOR_ID_NOT_EXIST)).thenReturn(false);
        assertAll("Существование id автора",
                () -> assertThat(authorProvider.existsById(AUTHOR_ID_EXIST)).isEqualTo(true),
                () -> assertThat(authorProvider.existsById(AUTHOR_ID_NOT_EXIST)).isEqualTo(false)
        );
    }

    @Test
    @DisplayName("проверять наличие автора по имени")
    void shouldCheckExistByName() {
        Mockito.when(authorRepository.existsByAuthorName(AUTHOR_NAME_EXISTS)).thenReturn(true);
        Mockito.when(authorRepository.existsByAuthorName(AUTHOR_NAME_NOT_EXISTS)).thenReturn(false);
        assertAll("Существование имени автора",
                () -> assertThat(authorProvider.existsByName(AUTHOR_NAME_EXISTS)).isEqualTo(true),
                () -> assertThat(authorProvider.existsByName(AUTHOR_NAME_NOT_EXISTS)).isEqualTo(false)
        );
    }
}