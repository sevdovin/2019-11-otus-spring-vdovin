package ru.otus.svdovin.homework13.service;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.svdovin.homework13.domain.Author;
import ru.otus.svdovin.homework13.repository.AuthorRepository;

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

    private static final String NEW_AUTHOR_ID = "idAuthor7";
    private static final String NEW_AUTHOR_NAME = "Вася";
    private static final String DEFAULT_AUTHOR_ID = "idAuthor4";
    private static final String AUTHOR_ID_EXIST = "idAuthor4";
    private static final String AUTHOR_ID_NOT_EXIST = "idAuthor5";
    private static final String AUTHOR_NAME_EXISTS = "Author 4";
    private static final String AUTHOR_NAME_NOT_EXISTS = "Author 5";

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorProvider authorProvider;

    @Test
    @DisplayName("должен корректно добавлять автора")
    void shouldCeateAuthor() {
        val author = Author.builder()
                .authorId(NEW_AUTHOR_ID)
                .authorName(NEW_AUTHOR_NAME)
                .build();
        Mockito.when(authorRepository.save(author)).thenReturn(author);
        String newId = authorProvider.createAuthor(author);
        assertThat(newId).isEqualTo(NEW_AUTHOR_ID);
    }

    @Test
    @DisplayName("возвращать ожидаемого автора по его id")
    void shouldReturnExpectedAuthorById() {
        val author = Author.builder()
                .authorId(NEW_AUTHOR_ID)
                .authorName(NEW_AUTHOR_NAME)
                .build();
        Mockito.when(authorRepository.findById(NEW_AUTHOR_ID)).thenReturn(Optional.of(author));
        Author actual = authorProvider.getAuthorById(NEW_AUTHOR_ID).orElse(null);
        assertThat(actual).isEqualToComparingFieldByField(author);
    }

    @Test
    @DisplayName("возвращать ожидаемого автора по его имени")
    void shouldReturnExpectedAuthorByName() {
        val author = Author.builder()
                .authorId(NEW_AUTHOR_ID)
                .authorName(NEW_AUTHOR_NAME)
                .build();
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
        val author = Author.builder()
                .authorId(NEW_AUTHOR_ID)
                .authorName(NEW_AUTHOR_NAME)
                .build();
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
        val author = Author.builder()
                .authorId(NEW_AUTHOR_ID)
                .authorName(NEW_AUTHOR_NAME)
                .build();
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